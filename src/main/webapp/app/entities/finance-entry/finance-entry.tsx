import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './finance-entry.reducer';
import { IFinanceEntry } from 'app/shared/model/finance-entry.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FinanceEntry = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const financeEntryList = useAppSelector(state => state.financeEntry.entities);
  const loading = useAppSelector(state => state.financeEntry.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="finance-entry-heading" data-cy="FinanceEntryHeading">
        <Translate contentKey="classesManagementApp.financeEntry.home.title">Finance Entries</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="classesManagementApp.financeEntry.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="classesManagementApp.financeEntry.home.createLabel">Create new Finance Entry</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {financeEntryList && financeEntryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="classesManagementApp.financeEntry.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="classesManagementApp.financeEntry.transactionDate">Transaction Date</Translate>
                </th>
                <th>
                  <Translate contentKey="classesManagementApp.financeEntry.amount">Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="classesManagementApp.financeEntry.notes">Notes</Translate>
                </th>
                <th>
                  <Translate contentKey="classesManagementApp.financeEntry.student">Student</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {financeEntryList.map((financeEntry, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${financeEntry.id}`} color="link" size="sm">
                      {financeEntry.id}
                    </Button>
                  </td>
                  <td>
                    {financeEntry.transactionDate ? (
                      <TextFormat type="date" value={financeEntry.transactionDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{financeEntry.amount}</td>
                  <td>{financeEntry.notes}</td>
                  <td>{financeEntry.student ? <Link to={`student/${financeEntry.student.id}`}>{financeEntry.student.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${financeEntry.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${financeEntry.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${financeEntry.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="classesManagementApp.financeEntry.home.notFound">No Finance Entries found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default FinanceEntry;
