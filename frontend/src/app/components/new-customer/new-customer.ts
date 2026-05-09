import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CustomerService } from '../../services/customer';

@Component({
  selector: 'app-new-customer',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './new-customer.html',
  styleUrl: './new-customer.css',
})
export class NewCustomerComponent {
  form = { name: '', email: '' };
  saving = false;
  error = '';

  constructor(private customerService: CustomerService, private router: Router) {}

  submit(): void {
    if (!this.form.name.trim() || !this.form.email.trim()) return;
    this.saving = true;
    this.customerService.saveCustomer(this.form).subscribe({
      next: () => this.router.navigate(['/customers']),
      error: () => { this.error = 'Failed to create customer. Please try again.'; this.saving = false; }
    });
  }
}
