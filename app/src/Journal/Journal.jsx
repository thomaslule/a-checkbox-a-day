import React from 'react';
import moment from 'moment';

import JournalEntryContainer from '../JournalEntry/JournalEntryContainer';

import './Journal.css';

class Journal extends React.Component {
  componentDidMount() {
    this.props.onChangeMonth(this.props.month);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.month !== this.props.month) {
      this.props.onChangeMonth(nextProps.month);
    }
  }

  getDaysOfMonth() {
    const start = moment(this.props.month, 'YYYY-MM');
    const dayRunner = moment(start);
    const days = [];
    while (dayRunner.month() === start.month()) {
      days.push({ day: dayRunner.format('YYYY-MM-DD'), text: '' });
      dayRunner.add(1, 'days');
    }
    return days;
  }

  render() {
    const journal = this.getDaysOfMonth()
      .map(entry => this.props.journal.find(e => e.day === entry.day) || entry)
      .map(entry => <JournalEntryContainer entry={entry} key={entry.day} />);
    return (<ul className="list-unstyled">{journal}</ul>);
  }
}

export default Journal;
