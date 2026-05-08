export type OperationType = 'DEBIT' | 'CREDIT';

export interface AccountOperation {
  id: number;
  operationDate: string;
  amount: number;
  type: OperationType;
  description: string;
}
