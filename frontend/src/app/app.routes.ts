import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { LoginComponent } from './components/login/login';
import { DashboardComponent } from './components/dashboard/dashboard';
import { CustomersComponent } from './components/customers/customers';
import { CustomerDetailComponent } from './components/customer-detail/customer-detail';
import { NewCustomerComponent } from './components/new-customer/new-customer';
import { AccountsComponent } from './components/accounts/accounts';
import { AccountDetailComponent } from './components/account-detail/account-detail';
import { NewOperationComponent } from './components/new-operation/new-operation';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard',               component: DashboardComponent,       canActivate: [authGuard] },
  { path: 'customers',               component: CustomersComponent,        canActivate: [authGuard] },
  { path: 'customers/new',           component: NewCustomerComponent,      canActivate: [authGuard] },
  { path: 'customers/:id',           component: CustomerDetailComponent,   canActivate: [authGuard] },
  { path: 'accounts',                component: AccountsComponent,         canActivate: [authGuard] },
  { path: 'accounts/:id',            component: AccountDetailComponent,    canActivate: [authGuard] },
  { path: 'accounts/:id/operation',  component: NewOperationComponent,     canActivate: [authGuard] }
];
