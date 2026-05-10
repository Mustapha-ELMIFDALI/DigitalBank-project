import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, ActivatedRoute } from '@angular/router';
import { BankAccountService } from '../../services/bank-account';
import { BankAccount, AccountHistory } from '../../models/bank-account.model';
import { AccountOperation } from '../../models/account-operation.model';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-account-detail',
  imports: [CommonModule, RouterLink],
  templateUrl: './account-detail.html',
  styleUrl: './account-detail.css',
})
export class AccountDetailComponent implements OnInit, AfterViewInit {
  @ViewChild('opsChart')  opsChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('typeChart') typeChartRef!: ElementRef<HTMLCanvasElement>;

  accountId = '';
  account: BankAccount | null = null;
  history: AccountHistory | null = null;
  operations: AccountOperation[] = [];
  currentPage = 0;
  pageSize = 5;
  loading = true;
  private chart1?: Chart;
  private chart2?: Chart;

  constructor(private route: ActivatedRoute, private svc: BankAccountService) {}

  ngOnInit(): void {
    this.accountId = this.route.snapshot.paramMap.get('id') ?? '';
    this.svc.getAccount(this.accountId).subscribe({
      next: (a) => { this.account = a; this.loading = false; },
      error: () => { this.loading = false; }
    });
    this.loadPage(0);
  }

  ngAfterViewInit(): void {}

  loadPage(page: number): void {
    this.currentPage = page;
    this.svc.getAccountHistory(this.accountId, page, this.pageSize).subscribe({
      next: (h) => { this.history = h; this.operations = h.accountOperationDTOS; setTimeout(() => this.buildCharts(), 100); },
      error: () => { this.operations = this.mockOps(); setTimeout(() => this.buildCharts(), 100); }
    });
  }

  get totalPages(): number { return this.history?.totalPages ?? 1; }
  get pages(): number[] { return Array.from({ length: this.totalPages }, (_, i) => i); }

  private buildCharts(): void {
    this.chart1?.destroy();
    this.chart2?.destroy();

    const credits = this.operations.filter(o => o.type === 'CREDIT');
    const debits  = this.operations.filter(o => o.type === 'DEBIT');
    const creditTotal = credits.reduce((s, o) => s + o.amount, 0);
    const debitTotal  = debits.reduce((s, o) => s + o.amount, 0);

    if (this.typeChartRef) {
      this.chart2 = new Chart(this.typeChartRef.nativeElement, {
        type: 'doughnut',
        data: {
          labels: ['Credits', 'Debits'],
          datasets: [{ data: [creditTotal, debitTotal],
            backgroundColor: ['#16a34a', '#dc2626'], borderWidth: 2, borderColor: '#fff' }]
        },
        options: { responsive: true, plugins: { legend: { position: 'bottom' } } }
      });
    }

    if (this.opsChartRef && this.operations.length) {
      this.chart1 = new Chart(this.opsChartRef.nativeElement, {
        type: 'bar',
        data: {
          labels: this.operations.map((_, i) => `Op ${i + 1}`),
          datasets: [{
            label: 'Amount (MAD)',
            data: this.operations.map(o => o.amount),
            backgroundColor: this.operations.map(o => o.type === 'CREDIT' ? '#16a34a' : '#dc2626'),
            borderRadius: 5
          }]
        },
        options: {
          responsive: true,
          plugins: { legend: { display: false } },
          scales: { y: { beginAtZero: true } }
        }
      });
    }
  }

  private mockOps(): AccountOperation[] {
    return [
      { id: 1, operationDate: '2026-04-01', amount: 5000,  type: 'CREDIT', description: 'Salary' },
      { id: 2, operationDate: '2026-04-03', amount: 1200,  type: 'DEBIT',  description: 'Rent' },
      { id: 3, operationDate: '2026-04-07', amount: 3000,  type: 'CREDIT', description: 'Transfer in' },
      { id: 4, operationDate: '2026-04-10', amount: 800,   type: 'DEBIT',  description: 'Shopping' },
      { id: 5, operationDate: '2026-04-15', amount: 15000, type: 'CREDIT', description: 'Bonus' },
    ];
  }
}
