import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { BankAccountService } from '../../services/bank-account';
import { BankAccount } from '../../models/bank-account.model';

@Component({
  selector: 'app-accounts',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css',
})
export class AccountsComponent implements OnInit {
  accounts: BankAccount[] = [];
  filtered: BankAccount[] = [];
  searchTerm = '';
  filterType = 'ALL';
  loading = true;

  constructor(private bankAccountService: BankAccountService) {}

  ngOnInit(): void { this.load(); }

  load(): void {
    this.loading = true;
    this.bankAccountService.getAccounts().subscribe({
      next: (a) => { this.accounts = a; this.applyFilter(); this.loading = false; },
      error: () => { this.accounts = this.mock(); this.applyFilter(); this.loading = false; }
    });
  }

  applyFilter(): void {
    let list = [...this.accounts];
    if (this.filterType !== 'ALL') list = list.filter(a => a.type === this.filterType);
    const t = this.searchTerm.toLowerCase();
    if (t) list = list.filter(a => a.id.toLowerCase().includes(t) || a.customerDTO?.name?.toLowerCase().includes(t));
    this.filtered = list;
  }

  totalBalance(): number { return this.filtered.reduce((s, a) => s + a.balance, 0); }

  private mock(): BankAccount[] {
    return [
      { id: 'a1b2c3d4-0001', balance: 45200, createdAt: '2026-01-10', status: 'ACTIVATED', customerDTO: { id:1, name:'Hassan Alami',  email:'hassan@proton.com' }, type: 'CurrentBankAccountDTO', overDraft: 9000 },
      { id: 'a1b2c3d4-0002', balance: 82000, createdAt: '2026-01-10', status: 'ACTIVATED', customerDTO: { id:1, name:'Hassan Alami',  email:'hassan@proton.com' }, type: 'SavingBankAccountDTO',  interestRate: 5.6 },
      { id: 'a1b2c3d4-0003', balance: 13500, createdAt: '2026-02-05', status: 'ACTIVATED', customerDTO: { id:2, name:'Ilyas Bousaid', email:'ilyas@proton.com'  }, type: 'CurrentBankAccountDTO', overDraft: 9000 },
      { id: 'a1b2c3d4-0004', balance: 67800, createdAt: '2026-02-05', status: 'SUSPENDED', customerDTO: { id:2, name:'Ilyas Bousaid', email:'ilyas@proton.com'  }, type: 'SavingBankAccountDTO',  interestRate: 5.6 },
      { id: 'a1b2c3d4-0005', balance: 29100, createdAt: '2026-03-01', status: 'ACTIVATED', customerDTO: { id:3, name:'Sdek Mahdi',    email:'sdek@proton.com'   }, type: 'CurrentBankAccountDTO', overDraft: 9000 },
      { id: 'a1b2c3d4-0006', balance: 54300, createdAt: '2026-03-01', status: 'CREATED',   customerDTO: { id:3, name:'Sdek Mahdi',    email:'sdek@proton.com'   }, type: 'SavingBankAccountDTO',  interestRate: 5.6 },
    ];
  }
}
