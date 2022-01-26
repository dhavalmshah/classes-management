import dayjs from 'dayjs';
import { IBatch } from 'app/shared/model/batch.model';
import { DayOfWeek } from 'app/shared/model/enumerations/day-of-week.model';

export interface IMockSchedule {
  id?: number;
  day?: DayOfWeek;
  timing?: string;
  batch?: IBatch | null;
}

export const defaultValue: Readonly<IMockSchedule> = {};
