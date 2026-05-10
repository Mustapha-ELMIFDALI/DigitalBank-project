import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { BankAccountService } from '../../services/bank-account';

type OpType = 'DEBIT' | 'CREDIT' | 'TRANSFER';

@Component({
  selector: 'app-new-operation',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './new-operation.html',
  styleUrl: './new-operation.css',
})
export class NewOperationComponent implements OnInit {
  accountId = '';
  opType: OpType = 'CREDIT';
  form = { amount: 0, description: '', destinationId: '' };
  saving = false;
  success = false;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private svc: BankAccountService
  ) {}

  ngOnInit(): void {
    this.accountId = this.route.snapshot.paramMap.get('id') ?? '';
  }

  submit(): void {
    if (!this.form.amount || this.form.amount <= 0) { this.error = 'Amount must be greater than 0.'; return; }
    this.saving = true; this.error = '';

    const obs =
      this.opType === 'DEBIT'    ? this.svc.debit(this.accountId, this.form.amount, this.form.description) :
      this.opType === 'CREDIT'   ? this.svc.credit(this.accountId, this.form.amount, this.form.description) :
      this.svc.transfer(this.accountId, this.form.destinationId, this.form.amount, this.form.description);

    obs.subscribe({
      next: () => { this.success = true; this.saving = false; setTimeout(() => this.router.navigate(['/accounts', this.accountId]), 1500); },
      error: (e) => { this.error = e?.error?.message ?? 'Operation failed. Please check details and try again.'; this.saving = false; }
    });
  }
}
