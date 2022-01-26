import { IEnrollment } from 'app/shared/model/enrollment.model';
import { ISchool } from 'app/shared/model/school.model';

export interface ICourse {
  id?: number;
  courseName?: string;
  courseCost?: number;
  enrollments?: IEnrollment[] | null;
  school?: ISchool | null;
}

export const defaultValue: Readonly<ICourse> = {};
