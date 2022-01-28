import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './batch.reducer';
import { IBatch } from 'app/shared/model/batch.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Batch = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const batchList = useAppSelector(state => state.batch.entities);
  const loading = useAppSelector(state => state.batch.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="batch-heading" data-cy="BatchHeading">
        <Translate contentKey="classesManagementApp.batch.home.title">Batches</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="classesManagementApp.batch.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="classesManagementApp.batch.home.createLabel">Create new Batch</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {batchList && batchList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="classesManagementApp.batch.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="classesManagementApp.batch.duration">Duration</Translate>
                </th>
                <th>
                  <Translate contentKey="classesManagementApp.batch.seats">Seats</Translate>
                </th>
                <th>
                  <Translate contentKey="classesManagementApp.batch.course">Course</Translate>
                </th>
                <th>
                  <Translate contentKey="classesManagementApp.batch.center">Center</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {batchList.map((batch, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${batch.id}`} color="link" size="sm">
                      {batch.id}
                    </Button>
                  </td>
                  <td>{batch.duration}</td>
                  <td>{batch.seats}</td>
                  <td>{batch.course ? <Link to={`course/${batch.course.id}`}>{batch.course.courseName}</Link> : ''}</td>
                  <td>{batch.center ? <Link to={`center/${batch.center.id}`}>{batch.center.centerName}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${batch.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${batch.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${batch.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="classesManagementApp.batch.home.notFound">No Batches found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Batch;
