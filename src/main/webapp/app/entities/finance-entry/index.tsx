import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FinanceEntry from './finance-entry';
import FinanceEntryDetail from './finance-entry-detail';
import FinanceEntryUpdate from './finance-entry-update';
import FinanceEntryDeleteDialog from './finance-entry-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FinanceEntryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FinanceEntryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FinanceEntryDetail} />
      <ErrorBoundaryRoute path={match.url} component={FinanceEntry} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FinanceEntryDeleteDialog} />
  </>
);

export default Routes;
