import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStudent } from 'app/shared/model/student.model';
import { getEntities as getStudents } from 'app/entities/student/student.reducer';
import { IBank } from 'app/shared/model/bank.model';
import { getEntities as getBanks } from 'app/entities/bank/bank.reducer';
import { IYear } from 'app/shared/model/year.model';
import { getEntities as getYears } from 'app/entities/year/year.reducer';
import { getEntity, updateEntity, createEntity, reset } from './finance-entry.reducer';
import { IFinanceEntry } from 'app/shared/model/finance-entry.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FinanceEntryUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const students = useAppSelector(state => state.student.entities);
  const banks = useAppSelector(state => state.bank.entities);
  const years = useAppSelector(state => state.year.entities);
  const financeEntryEntity = useAppSelector(state => state.financeEntry.entity);
  const loading = useAppSelector(state => state.financeEntry.loading);
  const updating = useAppSelector(state => state.financeEntry.updating);
  const updateSuccess = useAppSelector(state => state.financeEntry.updateSuccess);
  const handleClose = () => {
    props.history.push('/finance-entry');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getStudents({}));
    dispatch(getBanks({}));
    dispatch(getYears({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...financeEntryEntity,
      ...values,
      student: students.find(it => it.id.toString() === values.student.toString()),
      bank: banks.find(it => it.id.toString() === values.bank.toString()),
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
          ...financeEntryEntity,
          student: financeEntryEntity?.student?.id,
          bank: financeEntryEntity?.bank?.id,
          year: financeEntryEntity?.year?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="classesManagementApp.financeEntry.home.createOrEditLabel" data-cy="FinanceEntryCreateUpdateHeading">
            <Translate contentKey="classesManagementApp.financeEntry.home.createOrEditLabel">Create or edit a FinanceEntry</Translate>
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
                  id="finance-entry-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('classesManagementApp.financeEntry.transactionDate')}
                id="finance-entry-transactionDate"
                name="transactionDate"
                data-cy="transactionDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('classesManagementApp.financeEntry.amount')}
                id="finance-entry-amount"
                name="amount"
                data-cy="amount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('classesManagementApp.financeEntry.notes')}
                id="finance-entry-notes"
                name="notes"
                data-cy="notes"
                type="text"
              />
              <ValidatedField
                id="finance-entry-student"
                name="student"
                data-cy="student"
                label={translate('classesManagementApp.financeEntry.student')}
                type="select"
              >
                <option value="" key="0" />
                {students
                  ? students.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="finance-entry-bank"
                name="bank"
                data-cy="bank"
                label={translate('classesManagementApp.financeEntry.bank')}
                type="select"
              >
                <option value="" key="0" />
                {banks
                  ? banks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="finance-entry-year"
                name="year"
                data-cy="year"
                label={translate('classesManagementApp.financeEntry.year')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/finance-entry" replace color="info">
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

export default FinanceEntryUpdate;
