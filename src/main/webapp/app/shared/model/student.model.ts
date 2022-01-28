import { IEnrollment } from 'app/shared/model/enrollment.model';
import { ISchool } from 'app/shared/model/school.model';
import { IYear } from 'app/shared/model/year.model';
import { Standard } from 'app/shared/model/enumerations/standard.model';

export interface IStudent {
  id?: number;
  studentName?: string;
  standard?: Standard | null;
  studentPhone?: string | null;
  parentName?: string | null;
  parentPhone?: string | null;
  notes?: string | null;
  enrollments?: IEnrollment[] | null;
  school?: ISchool | null;
  year?: IYear | null;
}

export const defaultValue: Readonly<IStudent> = {};
