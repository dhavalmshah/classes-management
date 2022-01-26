import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './mock-schedule.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MockScheduleDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const mockScheduleEntity = useAppSelector(state => state.mockSchedule.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mockScheduleDetailsHeading">
          <Translate contentKey="classesManagementApp.mockSchedule.detail.title">MockSchedule</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{mockScheduleEntity.id}</dd>
          <dt>
            <span id="day">
              <Translate contentKey="classesManagementApp.mockSchedule.day">Day</Translate>
            </span>
          </dt>
          <dd>{mockScheduleEntity.day}</dd>
          <dt>
            <span id="timing">
              <Translate contentKey="classesManagementApp.mockSchedule.timing">Timing</Translate>
            </span>
          </dt>
          <dd>
            {mockScheduleEntity.timing ? <TextFormat value={mockScheduleEntity.timing} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="classesManagementApp.mockSchedule.batch">Batch</Translate>
          </dt>
          <dd>{mockScheduleEntity.batch ? mockScheduleEntity.batch.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/mock-schedule" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mock-schedule/${mockScheduleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MockScheduleDetail;
