import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bank.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bankEntity = useAppSelector(state => state.bank.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankDetailsHeading">
          <Translate contentKey="classesManagementApp.bank.detail.title">Bank</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bankEntity.id}</dd>
          <dt>
            <span id="accountName">
              <Translate contentKey="classesManagementApp.bank.accountName">Account Name</Translate>
            </span>
          </dt>
          <dd>{bankEntity.accountName}</dd>
          <dt>
            <span id="accountNumber">
              <Translate contentKey="classesManagementApp.bank.accountNumber">Account Number</Translate>
            </span>
          </dt>
          <dd>{bankEntity.accountNumber}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="classesManagementApp.bank.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{bankEntity.notes}</dd>
        </dl>
        <Button tag={Link} to="/bank" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bank/${bankEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankDetail;
