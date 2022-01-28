import { ISchool } from 'app/shared/model/school.model';
import { IYear } from 'app/shared/model/year.model';

export interface ICourse {
  id?: number;
  courseName?: string;
  courseCost?: number;
  duration?: number;
  seats?: number;
  notes?: string | null;
  school?: ISchool | null;
  year?: IYear | null;
}

export const defaultValue: Readonly<ICourse> = {};
