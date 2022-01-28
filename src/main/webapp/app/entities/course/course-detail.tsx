import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './course.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CourseDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const courseEntity = useAppSelector(state => state.course.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="courseDetailsHeading">
          <Translate contentKey="classesManagementApp.course.detail.title">Course</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{courseEntity.id}</dd>
          <dt>
            <span id="courseName">
              <Translate contentKey="classesManagementApp.course.courseName">Course Name</Translate>
            </span>
          </dt>
          <dd>{courseEntity.courseName}</dd>
          <dt>
            <span id="courseCost">
              <Translate contentKey="classesManagementApp.course.courseCost">Course Cost</Translate>
            </span>
          </dt>
          <dd>{courseEntity.courseCost}</dd>
          <dt>
            <span id="duration">
              <Translate contentKey="classesManagementApp.course.duration">Duration</Translate>
            </span>
          </dt>
          <dd>{courseEntity.duration}</dd>
          <dt>
            <span id="seats">
              <Translate contentKey="classesManagementApp.course.seats">Seats</Translate>
            </span>
          </dt>
          <dd>{courseEntity.seats}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="classesManagementApp.course.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{courseEntity.notes}</dd>
          <dt>
            <Translate contentKey="classesManagementApp.course.school">School</Translate>
          </dt>
          <dd>{courseEntity.school ? courseEntity.school.id : ''}</dd>
          <dt>
            <Translate contentKey="classesManagementApp.course.year">Year</Translate>
          </dt>
          <dd>{courseEntity.year ? courseEntity.year.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/course" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course/${courseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CourseDetail;
