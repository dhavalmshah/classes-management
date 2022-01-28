import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBatch } from 'app/shared/model/batch.model';
import { getEntities as getBatches } from 'app/entities/batch/batch.reducer';
import { getEntity, updateEntity, createEntity, reset } from './mock-schedule.reducer';
import { IMockSchedule } from 'app/shared/model/mock-schedule.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { DayOfWeek } from 'app/shared/model/enumerations/day-of-week.model';

export const MockScheduleUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const batches = useAppSelector(state => state.batch.entities);
  const mockScheduleEntity = useAppSelector(state => state.mockSchedule.entity);
  const loading = useAppSelector(state => state.mockSchedule.loading);
  const updating = useAppSelector(state => state.mockSchedule.updating);
  const updateSuccess = useAppSelector(state => state.mockSchedule.updateSuccess);
  const dayOfWeekValues = Object.keys(DayOfWeek);
  const handleClose = () => {
    props.history.push('/mock-schedule');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBatches({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.timing = convertDateTimeToServer(values.timing);

    const entity = {
      ...mockScheduleEntity,
      ...values,
      batch: batches.find(it => it.id.toString() === values.batch.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          timing: displayDefaultDateTime(),
        }
      : {
          day: 'Sunday',
          ...mockScheduleEntity,
          timing: convertDateTimeFromServer(mockScheduleEntity.timing),
          batch: mockScheduleEntity?.batch?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="classesManagementApp.mockSchedule.home.createOrEditLabel" data-cy="MockScheduleCreateUpdateHeading">
            <Translate contentKey="classesManagementApp.mockSchedule.home.createOrEditLabel">Create or edit a MockSchedule</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="mock-schedule-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('classesManagementApp.mockSchedule.day')}
                id="mock-schedule-day"
                name="day"
                data-cy="day"
                type="select"
              >
                {dayOfWeekValues.map(dayOfWeek => (
                  <option value={dayOfWeek} key={dayOfWeek}>
                    {translate('classesManagementApp.DayOfWeek.' + dayOfWeek)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('classesManagementApp.mockSchedule.timing')}
                id="mock-schedule-timing"
                name="timing"
                data-cy="timing"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="mock-schedule-batch"
                name="batch"
                data-cy="batch"
                label={translate('classesManagementApp.mockSchedule.batch')}
                type="select"
              >
                <option value="" key="0" />
                {batches
                  ? batches.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.course.courseName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/mock-schedule" replace color="info">
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

export default MockScheduleUpdate;
