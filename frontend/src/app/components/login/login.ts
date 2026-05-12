import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class LoginComponent {
  form = { username: '', password: '' };
  loading = false;
  error = '';
  showPassword = false;

  constructor(private authService: AuthService, private router: Router) {
    if (this.authService.isLoggedIn()) this.router.navigate(['/dashboard']);
  }

  submit(): void {
    if (!this.form.username || !this.form.password) {
      this.error = 'Please enter your credentials.';
      return;
    }
    this.loading = true;
    this.error = '';

    this.authService.login(this.form).subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: (e) => {
        this.error = e.status === 401
          ? 'Invalid username or password.'
          : 'Login failed. Please try again.';
        this.loading = false;
      }
    });
  }
}
