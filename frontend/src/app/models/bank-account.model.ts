import { Customer } from './customer.model';
import { AccountOperation } from './account-operation.model';

export type AccountStatus = 'CREATED' | 'ACTIVATED' | 'SUSPENDED';

export interface BankAccount {
  id: string;
  balance: number;
  createdAt: string;
  status: AccountStatus;
  customerDTO: Customer;
  type: 'CurrentBankAccountDTO' | 'SavingBankAccountDTO';
  overDraft?: number;
  interestRate?: number;
}

export interface AccountHistory {
  accountId: string;
  balance: number;
  currentPage: number;
  totalPages: number;
  pageSize: number;
  accountOperationDTOS: AccountOperation[];
}
