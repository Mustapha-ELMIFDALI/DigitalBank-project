import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CustomerService } from '../../services/customer';
import { BankAccountService } from '../../services/bank-account';
import { Customer } from '../../models/customer.model';
import { BankAccount } from '../../models/bank-account.model';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class DashboardComponent implements OnInit, AfterViewInit {
  @ViewChild('accountTypesChart') accountTypesRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('balanceChart')      balanceRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('operationsChart')   operationsRef!: ElementRef<HTMLCanvasElement>;

  customers: Customer[] = [];
  accounts: BankAccount[] = [];
  totalBalance = 0;
  currentAccounts = 0;
  savingAccounts = 0;
  loading = true;

  private charts: Chart[] = [];

  constructor(
    private customerService: CustomerService,
    private bankAccountService: BankAccountService
  ) {}

  ngOnInit(): void {
    this.customerService.getCustomers().subscribe({
      next: (c) => (this.customers = c),
      error: () => (this.customers = this.mockCustomers())
    });

    this.bankAccountService.getAccounts().subscribe({
      next: (a) => {
        this.accounts = a;
        this.computeStats();
        this.loading = false;
        setTimeout(() => this.buildCharts(), 100);
      },
      error: () => {
        this.accounts = this.mockAccounts();
        this.computeStats();
        this.loading = false;
        setTimeout(() => this.buildCharts(), 100);
      }
    });
  }

  ngAfterViewInit(): void {}

  private computeStats(): void {
    this.totalBalance    = this.accounts.reduce((s, a) => s + a.balance, 0);
    this.currentAccounts = this.accounts.filter(a => a.type === 'CurrentBankAccountDTO').length;
    this.savingAccounts  = this.accounts.filter(a => a.type === 'SavingBankAccountDTO').length;
  }

  private buildCharts(): void {
    this.charts.forEach(c => c.destroy());
    this.charts = [];

    // Pie — account types
    if (this.accountTypesRef) {
      this.charts.push(new Chart(this.accountTypesRef.nativeElement, {
        type: 'doughnut',
        data: {
          labels: ['Current Accounts', 'Saving Accounts'],
          datasets: [{ data: [this.currentAccounts, this.savingAccounts],
            backgroundColor: ['#1e3a8a','#3b82f6'], borderWidth: 2, borderColor: '#fff' }]
        },
        options: { responsive: true, plugins: { legend: { position: 'bottom' } } }
      }));
    }

    // Bar — balance per account (first 8)
    if (this.balanceRef) {
      const slice = this.accounts.slice(0, 8);
      this.charts.push(new Chart(this.balanceRef.nativeElement, {
        type: 'bar',
        data: {
          labels: slice.map(a => a.id.substring(0, 8) + '…'),
          datasets: [{ label: 'Balance (MAD)', data: slice.map(a => a.balance),
            backgroundColor: '#3b82f6', borderRadius: 6 }]
        },
        options: {
          responsive: true,
          plugins: { legend: { display: false } },
          scales: { y: { beginAtZero: true } }
        }
      }));
    }

    // Line — cumulative balance trend (mock monthly)
    if (this.operationsRef) {
      const months = ['Jan','Feb','Mar','Apr','May','Jun'];
      const base   = this.totalBalance * .5;
      const data   = months.map((_, i) => +(base + (Math.random() * base * .3 + i * base * .08)).toFixed(0));
      this.charts.push(new Chart(this.operationsRef.nativeElement, {
        type: 'line',
        data: {
          labels: months,
          datasets: [{
            label: 'Total Balance (MAD)',
            data,
            fill: true,
            backgroundColor: 'rgba(59,130,246,.12)',
            borderColor: '#1e3a8a',
            tension: .4,
            pointBackgroundColor: '#1e3a8a'
          }]
        },
        options: { responsive: true, plugins: { legend: { display: false } },
          scales: { y: { beginAtZero: false } } }
      }));
    }
  }

  // ── Mock data for when API is offline ──────
  private mockCustomers(): Customer[] {
    return [
      { id: 1, name: 'Hassan Alami',  email: 'hassan@proton.com' },
      { id: 2, name: 'Ilyas Bousaid', email: 'ilyas@proton.com'  },
      { id: 3, name: 'Sdek Mahdi',    email: 'sdek@proton.com'   }
    ];
  }

  private mockAccounts(): BankAccount[] {
    return [
      { id: 'acc-001', balance: 45200, createdAt: '2026-01-10', status: 'ACTIVATED', customerDTO: { id:1, name:'Hassan', email:'hassan@proton.com' }, type: 'CurrentBankAccountDTO', overDraft: 9000 },
      { id: 'acc-002', balance: 82000, createdAt: '2026-01-10', status: 'ACTIVATED', customerDTO: { id:1, name:'Hassan', email:'hassan@proton.com' }, type: 'SavingBankAccountDTO',  interestRate: 5.6 },
      { id: 'acc-003', balance: 13500, createdAt: '2026-02-05', status: 'ACTIVATED', customerDTO: { id:2, name:'Ilyas',  email:'ilyas@proton.com'  }, type: 'CurrentBankAccountDTO', overDraft: 9000 },
      { id: 'acc-004', balance: 67800, createdAt: '2026-02-05', status: 'SUSPENDED', customerDTO: { id:2, name:'Ilyas',  email:'ilyas@proton.com'  }, type: 'SavingBankAccountDTO',  interestRate: 5.6 },
    ];
  }
}
