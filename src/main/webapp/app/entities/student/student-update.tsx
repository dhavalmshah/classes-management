import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ISchool } from 'app/shared/model/school.model';
import { getEntities as getSchools } from 'app/entities/school/school.reducer';
import { IYear } from 'app/shared/model/year.model';
import { getEntities as getYears } from 'app/entities/year/year.reducer';
import { getEntity, updateEntity, createEntity, reset } from './student.reducer';
import { IStudent } from 'app/shared/model/student.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Standard } from 'app/shared/model/enumerations/standard.model';

export const StudentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const schools = useAppSelector(state => state.school.entities);
  const years = useAppSelector(state => state.year.entities);
  const studentEntity = useAppSelector(state => state.student.entity);
  const loading = useAppSelector(state => state.student.loading);
  const updating = useAppSelector(state => state.student.updating);
  const updateSuccess = useAppSelector(state => state.student.updateSuccess);
  const standardValues = Object.keys(Standard);
  const handleClose = () => {
    props.history.push('/student');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getSchools({}));
    dispatch(getYears({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...studentEntity,
      ...values,
      school: schools.find(it => it.id.toString() === values.school.toString()),
      year: years.find(it => it.id.toString() === values.year.toString()),
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
          standard: 'V',
          ...studentEntity,
          school: studentEntity?.school?.id,
          year: studentEntity?.year?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="classesManagementApp.student.home.createOrEditLabel" data-cy="StudentCreateUpdateHeading">
            <Translate contentKey="classesManagementApp.student.home.createOrEditLabel">Create or edit a Student</Translate>
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
                  id="student-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('classesManagementApp.student.studentName')}
                id="student-studentName"
                name="studentName"
                data-cy="studentName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('classesManagementApp.student.standard')}
                id="student-standard"
                name="standard"
                data-cy="standard"
                type="select"
              >
                {standardValues.map(standard => (
                  <option value={standard} key={standard}>
                    {translate('classesManagementApp.Standard.' + standard)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('classesManagementApp.student.studentPhone')}
                id="student-studentPhone"
                name="studentPhone"
                data-cy="studentPhone"
                type="text"
              />
              <ValidatedField
                label={translate('classesManagementApp.student.parentName')}
                id="student-parentName"
                name="parentName"
                data-cy="parentName"
                type="text"
              />
              <ValidatedField
                label={translate('classesManagementApp.student.parentPhone')}
                id="student-parentPhone"
                name="parentPhone"
                data-cy="parentPhone"
                type="text"
              />
              <ValidatedField
                label={translate('classesManagementApp.student.notes')}
                id="student-notes"
                name="notes"
                data-cy="notes"
                type="text"
              />
              <ValidatedField
                id="student-school"
                name="school"
                data-cy="school"
                label={translate('classesManagementApp.student.school')}
                type="select"
              >
                <option value="" key="0" />
                {schools
                  ? schools.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="student-year"
                name="year"
                data-cy="year"
                label={translate('classesManagementApp.student.year')}
                type="select"
              >
                <option value="" key="0" />
                {years
                  ? years.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/student" replace color="info">
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

export default StudentUpdate;
