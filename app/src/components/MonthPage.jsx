import React from 'react';
import moment from 'moment';
import {
  Redirect,
} from 'react-router-dom';
import JournalContainer from '../containers/JournalContainer';
import ItemListContainer from '../containers/ItemListContainer';
import MonthPicker from './MonthPicker';

function isMonthValid(month) {
  return moment(month, 'YYYY-MM', true).isValid();
}

export default (props) => {
  if (isMonthValid(props.match.params.month)) {
    return (
      <div className="row">
        <div className="col-xs-4">
          <JournalContainer month={props.match.params.month} />
        </div>
        <div className="col-xs-4">
          <MonthPicker month={props.match.params.month} />
          <ItemListContainer month={props.match.params.month} />
        </div>
      </div>);
  }
  return (<Redirect to="/" />);
};
