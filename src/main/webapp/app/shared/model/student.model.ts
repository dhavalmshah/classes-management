import { IEnrollment } from 'app/shared/model/enrollment.model';
import { Standard } from 'app/shared/model/enumerations/standard.model';

export interface IStudent {
  id?: number;
  studentName?: string;
  standard?: Standard | null;
  studentPhone?: string | null;
  parentName?: string | null;
  parentPhone?: string | null;
  enrollments?: IEnrollment[] | null;
}

export const defaultValue: Readonly<IStudent> = {};
