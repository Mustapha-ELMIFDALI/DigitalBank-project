import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CustomerService } from '../../services/customer';
import { Customer } from '../../models/customer.model';

@Component({
  selector: 'app-customers',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './customers.html',
  styleUrl: './customers.css',
})
export class CustomersComponent implements OnInit {
  customers: Customer[] = [];
  filtered: Customer[] = [];
  searchTerm = '';
  loading = true;
  deleteTarget: Customer | null = null;

  constructor(private customerService: CustomerService) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading = true;
    this.customerService.getCustomers().subscribe({
      next: (data) => { this.customers = data; this.applyFilter(); this.loading = false; },
      error: () => { this.customers = this.mock(); this.applyFilter(); this.loading = false; }
    });
  }

  applyFilter(): void {
    const t = this.searchTerm.toLowerCase();
    this.filtered = t
      ? this.customers.filter(c => c.name.toLowerCase().includes(t) || c.email.toLowerCase().includes(t))
      : [...this.customers];
  }

  confirmDelete(c: Customer): void { this.deleteTarget = c; }

  doDelete(): void {
    if (!this.deleteTarget) return;
    this.customerService.deleteCustomer(this.deleteTarget.id).subscribe({
      next: () => { this.load(); this.deleteTarget = null; },
      error: () => { this.deleteTarget = null; }
    });
  }

  private mock(): Customer[] {
    return [
      { id: 1, name: 'Hassan Alami',  email: 'hassan@proton.com' },
      { id: 2, name: 'Ilyas Bousaid', email: 'ilyas@proton.com'  },
      { id: 3, name: 'Sdek Mahdi',    email: 'sdek@proton.com'   }
    ];
  }
}
