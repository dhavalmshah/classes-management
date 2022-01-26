import { IFinanceEntry } from 'app/shared/model/finance-entry.model';
import { IStudent } from 'app/shared/model/student.model';
import { ICourse } from 'app/shared/model/course.model';

export interface IEnrollment {
  id?: number;
  notes?: string;
  fees?: IFinanceEntry | null;
  student?: IStudent | null;
  course?: ICourse | null;
}

export const defaultValue: Readonly<IEnrollment> = {};
