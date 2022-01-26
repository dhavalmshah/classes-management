import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './student.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StudentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const studentEntity = useAppSelector(state => state.student.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="studentDetailsHeading">
          <Translate contentKey="classesManagementApp.student.detail.title">Student</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{studentEntity.id}</dd>
          <dt>
            <span id="studentName">
              <Translate contentKey="classesManagementApp.student.studentName">Student Name</Translate>
            </span>
          </dt>
          <dd>{studentEntity.studentName}</dd>
          <dt>
            <span id="standard">
              <Translate contentKey="classesManagementApp.student.standard">Standard</Translate>
            </span>
          </dt>
          <dd>{studentEntity.standard}</dd>
          <dt>
            <span id="studentPhone">
              <Translate contentKey="classesManagementApp.student.studentPhone">Student Phone</Translate>
            </span>
          </dt>
          <dd>{studentEntity.studentPhone}</dd>
          <dt>
            <span id="parentName">
              <Translate contentKey="classesManagementApp.student.parentName">Parent Name</Translate>
            </span>
          </dt>
          <dd>{studentEntity.parentName}</dd>
          <dt>
            <span id="parentPhone">
              <Translate contentKey="classesManagementApp.student.parentPhone">Parent Phone</Translate>
            </span>
          </dt>
          <dd>{studentEntity.parentPhone}</dd>
          <dt>
            <Translate contentKey="classesManagementApp.student.school">School</Translate>
          </dt>
          <dd>{studentEntity.school ? studentEntity.school.schoolName : ''}</dd>
        </dl>
        <Button tag={Link} to="/student" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/student/${studentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StudentDetail;
