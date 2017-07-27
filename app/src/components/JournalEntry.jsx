import React from "react";
import moment from "moment";

import EditableText from "./EditableText";

class JournalEntry extends React.Component {
    render() {
        return (
            <li className="journal-entry">
                <time dateTime="{moment(this.props.entry.day, 'YYYY-MM-DD').format()}">{moment(this.props.entry.day, 'YYYY-MM-DD').format('dddd D')}</time>
                <EditableText text={this.props.entry.text} onChangeText={(text) => this.props.onChangeText(text)} />
            </li>);
    }
}

export default JournalEntry;
