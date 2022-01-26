import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './batch.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BatchDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const batchEntity = useAppSelector(state => state.batch.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="batchDetailsHeading">
          <Translate contentKey="classesManagementApp.batch.detail.title">Batch</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{batchEntity.id}</dd>
          <dt>
            <span id="duration">
              <Translate contentKey="classesManagementApp.batch.duration">Duration</Translate>
            </span>
          </dt>
          <dd>{batchEntity.duration}</dd>
          <dt>
            <span id="seats">
              <Translate contentKey="classesManagementApp.batch.seats">Seats</Translate>
            </span>
          </dt>
          <dd>{batchEntity.seats}</dd>
          <dt>
            <Translate contentKey="classesManagementApp.batch.course">Course</Translate>
          </dt>
          <dd>{batchEntity.course ? batchEntity.course.id : ''}</dd>
          <dt>
            <Translate contentKey="classesManagementApp.batch.center">Center</Translate>
          </dt>
          <dd>{batchEntity.center ? batchEntity.center.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/batch" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/batch/${batchEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BatchDetail;
