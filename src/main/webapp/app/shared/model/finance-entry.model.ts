import dayjs from 'dayjs';
import { IStudent } from 'app/shared/model/student.model';
import { IBank } from 'app/shared/model/bank.model';
import { IYear } from 'app/shared/model/year.model';

export interface IFinanceEntry {
  id?: number;
  transactionDate?: string;
  amount?: number;
  notes?: string | null;
  student?: IStudent | null;
  bank?: IBank | null;
  year?: IYear | null;
}

export const defaultValue: Readonly<IFinanceEntry> = {};
