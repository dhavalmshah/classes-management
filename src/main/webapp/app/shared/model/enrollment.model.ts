import { IFinanceEntry } from 'app/shared/model/finance-entry.model';
import { IStudent } from 'app/shared/model/student.model';
import { IBatch } from 'app/shared/model/batch.model';

export interface IEnrollment {
  id?: number;
  notes?: string;
  fees?: IFinanceEntry | null;
  student?: IStudent | null;
  course?: IBatch | null;
}

export const defaultValue: Readonly<IEnrollment> = {};
