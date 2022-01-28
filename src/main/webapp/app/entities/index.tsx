import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import School from './school';
import Center from './center';
import Student from './student';
import Course from './course';
import Batch from './batch';
import FinanceEntry from './finance-entry';
import MockSchedule from './mock-schedule';
import Enrollment from './enrollment';
import Year from './year';
import Bank from './bank';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}school`} component={School} />
      <ErrorBoundaryRoute path={`${match.url}center`} component={Center} />
      <ErrorBoundaryRoute path={`${match.url}student`} component={Student} />
      <ErrorBoundaryRoute path={`${match.url}course`} component={Course} />
      <ErrorBoundaryRoute path={`${match.url}batch`} component={Batch} />
      <ErrorBoundaryRoute path={`${match.url}finance-entry`} component={FinanceEntry} />
      <ErrorBoundaryRoute path={`${match.url}mock-schedule`} component={MockSchedule} />
      <ErrorBoundaryRoute path={`${match.url}enrollment`} component={Enrollment} />
      <ErrorBoundaryRoute path={`${match.url}year`} component={Year} />
      <ErrorBoundaryRoute path={`${match.url}bank`} component={Bank} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
