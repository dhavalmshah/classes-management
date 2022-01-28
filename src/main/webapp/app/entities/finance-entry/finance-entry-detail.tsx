import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './finance-entry.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FinanceEntryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const financeEntryEntity = useAppSelector(state => state.financeEntry.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="financeEntryDetailsHeading">
          <Translate contentKey="classesManagementApp.financeEntry.detail.title">FinanceEntry</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{financeEntryEntity.id}</dd>
          <dt>
            <span id="transactionDate">
              <Translate contentKey="classesManagementApp.financeEntry.transactionDate">Transaction Date</Translate>
            </span>
          </dt>
          <dd>
            {financeEntryEntity.transactionDate ? (
              <TextFormat value={financeEntryEntity.transactionDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="amount">
              <Translate contentKey="classesManagementApp.financeEntry.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{financeEntryEntity.amount}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="classesManagementApp.financeEntry.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{financeEntryEntity.notes}</dd>
          <dt>
            <Translate contentKey="classesManagementApp.financeEntry.student">Student</Translate>
          </dt>
          <dd>{financeEntryEntity.student ? financeEntryEntity.student.id : ''}</dd>
          <dt>
            <Translate contentKey="classesManagementApp.financeEntry.bank">Bank</Translate>
          </dt>
          <dd>{financeEntryEntity.bank ? financeEntryEntity.bank.id : ''}</dd>
          <dt>
            <Translate contentKey="classesManagementApp.financeEntry.year">Year</Translate>
          </dt>
          <dd>{financeEntryEntity.year ? financeEntryEntity.year.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/finance-entry" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/finance-entry/${financeEntryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FinanceEntryDetail;
