import React from 'react';
import moment from 'moment';
import { Link } from 'react-router-dom';

class MonthPicker extends React.Component {
  getCurrentMonthDisplay() {
    const currentMonth = moment(this.props.month);
    return currentMonth.format('MMMM YYYY');
  }

  getPreviousMonthLink() {
    const month = moment(this.props.month);
    month.subtract(1, 'month');
    return `/month/${month.format('YYYY-MM')}`;
  }

  getNextMonthLink() {
    const month = moment(this.props.month);
    month.add(1, 'month');
    return `/month/${month.format('YYYY-MM')}`;
  }

  render() {
    return (
      <div className="row">
        <h1 className="col-xs-12 text-center">
          <Link to={this.getPreviousMonthLink()}>&larr;</Link>
          <span>{this.getCurrentMonthDisplay()}</span>
          <Link to={this.getNextMonthLink()}>&rarr;</Link>
        </h1>
      </div>
    );
  }
}

export default MonthPicker;
