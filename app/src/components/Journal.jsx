import React from "react";
import moment from "moment";

import JournalEntryContainer from "../containers/JournalEntryContainer";

import "./Journal.css";

class Journal extends React.Component {

    componentDidMount() {
        this.props.onChangeMonth(this.props.month);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.month !== this.props.month) {
            this.props.onChangeMonth(nextProps.month);
        }
    }

    render() {
        let journal = this.getDaysOfMonth()
            .map(entry => this.props.journal.find(e => e.day === entry.day) || entry)
            .map(entry => <JournalEntryContainer entry={entry} key={entry.day} />);
        return (<ul className="list-unstyled">{journal}</ul>);
    }

    getDaysOfMonth() {
        let start = moment(this.props.month, "YYYY-MM");
        let dayRunner = moment(start);
        let days = [];
        while (dayRunner.month() === start.month()) {
            days.push({ day: dayRunner.format("YYYY-MM-DD"), text: "" });
            dayRunner.add(1, "days");
        }
        return days;
    }

}

export default Journal;
