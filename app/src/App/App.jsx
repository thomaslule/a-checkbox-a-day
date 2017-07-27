import React from 'react';
import {
  BrowserRouter as Router,
  Route,
  Redirect,
  Switch,
} from 'react-router-dom';
import moment from 'moment';
import 'bootstrap/dist/css/bootstrap.css';

import MonthPage from '../MonthPage/MonthPage';
import ErrorContainer from '../Error/ErrorContainer';

function defaultPage() {
  return `/month/${moment().format('YYYY-MM')}`;
}

export default props => (
  <div>
    <nav className="navbar navbar-default navbar-static-top">
      <div className="container">
        <span className="navbar-brand">A checkbox a day</span>
      </div>
    </nav>
    <ErrorContainer />
    <div className="container">
      <Router>
        <Switch>
          <Route
            exact
            path="/month/:month"
            component={MonthPage}
          />
          <Redirect from="*" to={defaultPage()} />
        </Switch>
      </Router>
    </div>
  </div>
);
