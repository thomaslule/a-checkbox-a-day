import React from "react";
import moment from "moment";
import {
    Redirect
} from "react-router-dom";
import JournalContainer from "../containers/JournalContainer";
import ItemListContainer from "../containers/ItemListContainer";
import MonthPicker from "./MonthPicker";

class MonthPage extends React.Component {

    render() {
        if (this.isMonthValid(this.props.match.params.month)) {
            return (
                <div className="row">
                    <div className="col-xs-4">
                        <JournalContainer month={this.props.match.params.month} />
                    </div>
                    <div className="col-xs-4">
                        <MonthPicker month={this.props.match.params.month} />
                        <ItemListContainer month={this.props.match.params.month} />
                    </div>
                </div>);
        } else {
            return (<Redirect to="/" />);
        }
    }

    isMonthValid(month) {
        return moment(month, "YYYY-MM", true).isValid();
    }
}

export default MonthPage;
