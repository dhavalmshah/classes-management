import dayjs from 'dayjs';
import { IStudent } from 'app/shared/model/student.model';

export interface IFinanceEntry {
  id?: number;
  transactionDate?: string;
  amount?: number;
  notes?: string | null;
  student?: IStudent | null;
}

export const defaultValue: Readonly<IFinanceEntry> = {};
