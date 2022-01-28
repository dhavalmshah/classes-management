import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IFinanceEntry } from 'app/shared/model/finance-entry.model';
import { getEntities as getFinanceEntries } from 'app/entities/finance-entry/finance-entry.reducer';
import { IStudent } from 'app/shared/model/student.model';
import { getEntities as getStudents } from 'app/entities/student/student.reducer';
import { ICourse } from 'app/shared/model/course.model';
import { getEntities as getCourses } from 'app/entities/course/course.reducer';
import { getEntity, updateEntity, createEntity, reset } from './enrollment.reducer';
import { IEnrollment } from 'app/shared/model/enrollment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const EnrollmentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const financeEntries = useAppSelector(state => state.financeEntry.entities);
  const students = useAppSelector(state => state.student.entities);
  const courses = useAppSelector(state => state.course.entities);
  const enrollmentEntity = useAppSelector(state => state.enrollment.entity);
  const loading = useAppSelector(state => state.enrollment.loading);
  const updating = useAppSelector(state => state.enrollment.updating);
  const updateSuccess = useAppSelector(state => state.enrollment.updateSuccess);
  const handleClose = () => {
    props.history.push('/enrollment');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getFinanceEntries({}));
    dispatch(getStudents({}));
    dispatch(getCourses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...enrollmentEntity,
      ...values,
      fees: financeEntries.find(it => it.id.toString() === values.fees.toString()),
      student: students.find(it => it.id.toString() === values.student.toString()),
      course: courses.find(it => it.id.toString() === values.course.toString()),
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
          ...enrollmentEntity,
          fees: enrollmentEntity?.fees?.id,
          student: enrollmentEntity?.student?.id,
          course: enrollmentEntity?.course?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="classesManagementApp.enrollment.home.createOrEditLabel" data-cy="EnrollmentCreateUpdateHeading">
            <Translate contentKey="classesManagementApp.enrollment.home.createOrEditLabel">Create or edit a Enrollment</Translate>
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
                  id="enrollment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('classesManagementApp.enrollment.notes')}
                id="enrollment-notes"
                name="notes"
                data-cy="notes"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="enrollment-fees"
                name="fees"
                data-cy="fees"
                label={translate('classesManagementApp.enrollment.fees')}
                type="select"
              >
                <option value="" key="0" />
                {financeEntries
                  ? financeEntries.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="enrollment-student"
                name="student"
                data-cy="student"
                label={translate('classesManagementApp.enrollment.student')}
                type="select"
              >
                <option value="" key="0" />
                {students
                  ? students.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.studentName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="enrollment-course"
                name="course"
                data-cy="course"
                label={translate('classesManagementApp.enrollment.course')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/enrollment" replace color="info">
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

export default EnrollmentUpdate;
