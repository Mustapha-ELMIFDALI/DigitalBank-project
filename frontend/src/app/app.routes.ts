import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard';
import { CustomersComponent } from './components/customers/customers';
import { CustomerDetailComponent } from './components/customer-detail/customer-detail';
import { NewCustomerComponent } from './components/new-customer/new-customer';
import { AccountsComponent } from './components/accounts/accounts';
import { AccountDetailComponent } from './components/account-detail/account-detail';
import { NewOperationComponent } from './components/new-operation/new-operation';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'customers', component: CustomersComponent },
  { path: 'customers/new', component: NewCustomerComponent },
  { path: 'customers/:id', component: CustomerDetailComponent },
  { path: 'accounts', component: AccountsComponent },
  { path: 'accounts/:id', component: AccountDetailComponent },
  { path: 'accounts/:id/operation', component: NewOperationComponent }
];
