import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Year from './year';
import YearDetail from './year-detail';
import YearUpdate from './year-update';
import YearDeleteDialog from './year-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={YearUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={YearUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={YearDetail} />
      <ErrorBoundaryRoute path={match.url} component={Year} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={YearDeleteDialog} />
  </>
);

export default Routes;
