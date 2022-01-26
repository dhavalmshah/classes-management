import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MockSchedule from './mock-schedule';
import MockScheduleDetail from './mock-schedule-detail';
import MockScheduleUpdate from './mock-schedule-update';
import MockScheduleDeleteDialog from './mock-schedule-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MockScheduleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MockScheduleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MockScheduleDetail} />
      <ErrorBoundaryRoute path={match.url} component={MockSchedule} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MockScheduleDeleteDialog} />
  </>
);

export default Routes;
