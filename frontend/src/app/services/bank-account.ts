import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BankAccount, AccountHistory } from '../models/bank-account.model';
import { AccountOperation } from '../models/account-operation.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class BankAccountService {
  private api = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getAccounts(): Observable<BankAccount[]> {
    return this.http.get<BankAccount[]>(`${this.api}/accounts`);
  }

  getAccount(id: string): Observable<BankAccount> {
    return this.http.get<BankAccount>(`${this.api}/accounts/${id}`);
  }

  getAccountOperations(id: string): Observable<AccountOperation[]> {
    return this.http.get<AccountOperation[]>(`${this.api}/accounts/${id}/operations`);
  }

  getAccountHistory(id: string, page = 0, size = 10): Observable<AccountHistory> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<AccountHistory>(`${this.api}/accounts/${id}/pageOperations`, { params });
  }

  debit(accountId: string, amount: number, description: string): Observable<void> {
    return this.http.post<void>(`${this.api}/accounts/debit`, { accountId, amount, description });
  }

  credit(accountId: string, amount: number, description: string): Observable<void> {
    return this.http.post<void>(`${this.api}/accounts/credit`, { accountId, amount, description });
  }

  transfer(accountSource: string, accountDestination: string, amount: number, description: string): Observable<void> {
    return this.http.post<void>(`${this.api}/accounts/transfer`, {
      accountSource, accountDestination, amount, description
    });
  }
}
