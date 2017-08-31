import React from 'react';
import moment from 'moment';

class MoveItemButton extends React.Component {
  getNearbyMonths() {
    const currentMonth = moment(this.props.item.month, 'YYYY-MM');
    return [
      moment(currentMonth).add(1, 'month'),
      moment(currentMonth).add(2, 'month'),
      moment(currentMonth).add(3, 'month'),
    ];
  }

  getNearbyMonthsItems() {
    return this.getNearbyMonths().map(month => (<li key={month}><a href="" onClick={e => this.handleClick(e, month)}>{month.format('MMMM YYYY')}</a></li>));
  }

  handleClick(e, month) {
    e.preventDefault();
    this.props.onMove(month.format('YYYY-MM'));
  }

  render() {
    return (<span className="dropdown">
      <a href="" title="Move" className="dropdown-toggle" data-toggle="dropdown"><i className="glyphicon glyphicon-arrow-right" /></a>
      <ul className="dropdown-menu">
        <li className="dropdown-header">Move to…</li>
        {this.getNearbyMonthsItems()}
      </ul>
    </span>);
  }
}

export default MoveItemButton;
