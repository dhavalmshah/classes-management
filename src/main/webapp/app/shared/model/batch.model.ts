import { IMockSchedule } from 'app/shared/model/mock-schedule.model';
import { ICourse } from 'app/shared/model/course.model';
import { ICenter } from 'app/shared/model/center.model';

export interface IBatch {
  id?: number;
  duration?: number;
  seats?: number;
  mockSchedules?: IMockSchedule[] | null;
  course?: ICourse | null;
  center?: ICenter | null;
}

export const defaultValue: Readonly<IBatch> = {};
