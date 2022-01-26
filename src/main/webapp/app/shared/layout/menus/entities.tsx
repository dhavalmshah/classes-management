import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/school">
      <Translate contentKey="global.menu.entities.school" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/center">
      <Translate contentKey="global.menu.entities.center" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/student">
      <Translate contentKey="global.menu.entities.student" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course">
      <Translate contentKey="global.menu.entities.course" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/batch">
      <Translate contentKey="global.menu.entities.batch" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/finance-entry">
      <Translate contentKey="global.menu.entities.financeEntry" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/mock-schedule">
      <Translate contentKey="global.menu.entities.mockSchedule" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/enrollment">
      <Translate contentKey="global.menu.entities.enrollment" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
