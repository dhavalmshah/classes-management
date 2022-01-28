import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './mock-schedule.reducer';
import { IMockSchedule } from 'app/shared/model/mock-schedule.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MockSchedule = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const mockScheduleList = useAppSelector(state => state.mockSchedule.entities);
  const loading = useAppSelector(state => state.mockSchedule.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="mock-schedule-heading" data-cy="MockScheduleHeading">
        <Translate contentKey="classesManagementApp.mockSchedule.home.title">Mock Schedules</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="classesManagementApp.mockSchedule.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="classesManagementApp.mockSchedule.home.createLabel">Create new Mock Schedule</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {mockScheduleList && mockScheduleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="classesManagementApp.mockSchedule.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="classesManagementApp.mockSchedule.day">Day</Translate>
                </th>
                <th>
                  <Translate contentKey="classesManagementApp.mockSchedule.timing">Timing</Translate>
                </th>
                <th>
                  <Translate contentKey="classesManagementApp.mockSchedule.batch">Batch</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mockScheduleList.map((mockSchedule, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${mockSchedule.id}`} color="link" size="sm">
                      {mockSchedule.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`classesManagementApp.DayOfWeek.${mockSchedule.day}`} />
                  </td>
                  <td>{mockSchedule.timing ? <TextFormat type="date" value={mockSchedule.timing} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {mockSchedule.batch ? <Link to={`batch/${mockSchedule.batch.id}`}>{mockSchedule.batch.course.courseName}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mockSchedule.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mockSchedule.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${mockSchedule.id}/delete`}
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
              <Translate contentKey="classesManagementApp.mockSchedule.home.notFound">No Mock Schedules found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MockSchedule;
