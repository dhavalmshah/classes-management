import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Bank from './bank';
import BankDetail from './bank-detail';
import BankUpdate from './bank-update';
import BankDeleteDialog from './bank-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankDetail} />
      <ErrorBoundaryRoute path={match.url} component={Bank} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BankDeleteDialog} />
  </>
);

export default Routes;
