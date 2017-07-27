import React from "react";
import moment from "moment";

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
            .map(entry => <li key={entry.day}>{entry.day} {entry.text}</li>);
        return (<ul>{journal}</ul>);
    }

    getDaysOfMonth() {
        let start = moment(this.props.month, "YYYY-MM");
        let dayRunner = moment(start);
        let days = [];
        while (dayRunner.month() == start.month()) {
            days.push({ day: dayRunner.format("YYYY-MM-DD"), text: "" });
            dayRunner.add(1, "days");
        }
        return days;
    }

}

export default Journal;
