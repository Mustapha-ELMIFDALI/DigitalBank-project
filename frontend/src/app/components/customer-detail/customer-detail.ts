import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CustomerService } from '../../services/customer';
import { BankAccountService } from '../../services/bank-account';
import { Customer } from '../../models/customer.model';
import { BankAccount } from '../../models/bank-account.model';

@Component({
  selector: 'app-customer-detail',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './customer-detail.html',
  styleUrl: './customer-detail.css',
})
export class CustomerDetailComponent implements OnInit {
  customer: Customer | null = null;
  accounts: BankAccount[] = [];
  editMode = false;
  editForm = { name: '', email: '' };
  saving = false;
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private customerService: CustomerService,
    private bankAccountService: BankAccountService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.customerService.getCustomer(id).subscribe({
      next: (c) => { this.customer = c; this.editForm = { name: c.name, email: c.email }; this.loading = false; },
      error: () => this.loading = false
    });
    this.bankAccountService.getAccounts().subscribe({
      next: (all) => this.accounts = all.filter(a => a.customerDTO.id === id),
      error: () => {}
    });
  }

  startEdit(): void { this.editMode = true; }

  saveEdit(): void {
    if (!this.customer) return;
    this.saving = true;
    this.customerService.updateCustomer(this.customer.id, this.editForm).subscribe({
      next: (updated) => { this.customer = updated; this.editMode = false; this.saving = false; },
      error: () => { this.saving = false; }
    });
  }

  totalBalance(): number {
    return this.accounts.reduce((s, a) => s + a.balance, 0);
  }
}
