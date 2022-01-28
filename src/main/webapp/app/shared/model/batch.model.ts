import { IMockSchedule } from 'app/shared/model/mock-schedule.model';
import { IEnrollment } from 'app/shared/model/enrollment.model';
import { ICourse } from 'app/shared/model/course.model';
import { ICenter } from 'app/shared/model/center.model';
import { IYear } from 'app/shared/model/year.model';

export interface IBatch {
  id?: number;
  name?: string | null;
  notes?: string | null;
  mockSchedules?: IMockSchedule[] | null;
  enrollments?: IEnrollment[] | null;
  course?: ICourse | null;
  center?: ICenter | null;
  year?: IYear | null;
}

export const defaultValue: Readonly<IBatch> = {};
