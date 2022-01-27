import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICourse } from 'app/shared/model/course.model';
import { getEntities as getCourses } from 'app/entities/course/course.reducer';
import { ICenter } from 'app/shared/model/center.model';
import { getEntities as getCenters } from 'app/entities/center/center.reducer';
import { getEntity, updateEntity, createEntity, reset } from './batch.reducer';
import { IBatch } from 'app/shared/model/batch.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import moment from 'moment';
import { Calendar, Views, momentLocalizer, Event as eve } from 'react-big-calendar';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import './batch.css';

const localizer = momentLocalizer(moment);

export const BatchUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);
  const slotStyle = { className: 'super-time-slot' };

  const applyStyles = () => slotStyle;

  const courses = useAppSelector(state => state.course.entities);
  const centers = useAppSelector(state => state.center.entities);
  const batchEntity = useAppSelector(state => state.batch.entity);
  const loading = useAppSelector(state => state.batch.loading);
  const updating = useAppSelector(state => state.batch.updating);
  const updateSuccess = useAppSelector(state => state.batch.updateSuccess);
  const handleClose = () => {
    props.history.push('/batch');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCourses({}));
    dispatch(getCenters({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const ev: eve = {};
  const [events, setEvents] = useState([]);
  // eslint-disable-next-line
  const handleSelect = (slotInfo: any) => {
    setEvents(events.concat([getEventFromSlotInfo(slotInfo)]));
  };
  const getEventFromSlotInfo = (slotInfo: any) => {
    const evFromInfo: eve = {};
    evFromInfo.start = slotInfo.start;
    evFromInfo.title = batchEntity.course.courseName;
    evFromInfo.end = moment(slotInfo.start).add(batchEntity.duration, 'm').toDate();
    return evFromInfo;
  };

  const saveEntity = values => {
    const entity = {
      ...batchEntity,
      ...values,
      course: courses.find(it => it.id.toString() === values.course.toString()),
      center: centers.find(it => it.id.toString() === values.center.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...batchEntity,
          course: batchEntity?.course?.id,
          center: batchEntity?.center?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="classesManagementApp.batch.home.createOrEditLabel" data-cy="BatchCreateUpdateHeading">
            <Translate contentKey="classesManagementApp.batch.home.createOrEditLabel">Create or edit a Batch</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              <h1>{events.length}</h1>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="batch-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('classesManagementApp.batch.duration')}
                id="batch-duration"
                name="duration"
                data-cy="duration"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 30, message: translate('entity.validation.min', { min: 30 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('classesManagementApp.batch.seats')}
                id="batch-seats"
                name="seats"
                data-cy="seats"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 1, message: translate('entity.validation.min', { min: 1 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="batch-course"
                name="course"
                data-cy="course"
                label={translate('classesManagementApp.batch.course')}
                type="select"
              >
                <option value="" key="0" />
                {courses
                  ? courses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.courseName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="batch-center"
                name="center"
                data-cy="center"
                label={translate('classesManagementApp.batch.center')}
                type="select"
              >
                <option value="" key="0" />
                {centers
                  ? centers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.centerName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Calendar
                selectable
                localizer={localizer}
                events={events}
                views={['week']}
                toolbar={false}
                defaultView={Views.WEEK}
                scrollToTime={new Date()}
                defaultDate={new Date()}
                onSelectEvent={event => alert(event.title)}
                onSelectSlot={handleSelect}
                slotPropGetter={applyStyles}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/batch" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BatchUpdate;
