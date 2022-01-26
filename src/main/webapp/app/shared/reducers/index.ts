import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import school from 'app/entities/school/school.reducer';
// prettier-ignore
import center from 'app/entities/center/center.reducer';
// prettier-ignore
import student from 'app/entities/student/student.reducer';
// prettier-ignore
import course from 'app/entities/course/course.reducer';
// prettier-ignore
import batch from 'app/entities/batch/batch.reducer';
// prettier-ignore
import financeEntry from 'app/entities/finance-entry/finance-entry.reducer';
// prettier-ignore
import mockSchedule from 'app/entities/mock-schedule/mock-schedule.reducer';
// prettier-ignore
import enrollment from 'app/entities/enrollment/enrollment.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  school,
  center,
  student,
  course,
  batch,
  financeEntry,
  mockSchedule,
  enrollment,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
