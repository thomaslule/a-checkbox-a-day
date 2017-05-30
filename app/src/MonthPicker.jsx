import React from "react";
import moment from "moment";
import { Link } from "react-router-dom";

class MonthPicker extends React.Component {
    
    render() {
        return (
            <div className="row">
                <h1 className="col-xs-12 text-center">
                    <Link to={"/" + this.getPreviousMonthCode()}>&larr;</Link>
                    <span>{this.getCurrentMonthDisplay()}</span>
                    <Link to={"/" + this.getNextMonthCode()}>&rarr;</Link>
                </h1>
            </div>
        );
    }
    
    getCurrentMonthDisplay() {
        let currentMonth = moment(this.props.match.params.month);
        return currentMonth.format("MMMM YYYY");
    }
    
    getPreviousMonthCode() {
        let month = moment(this.props.match.params.month);
        month.subtract(1, "month");
        return month.format("YYYY-MM");
    }
    
    getNextMonthCode() {
        let month = moment(this.props.match.params.month);
        month.add(1, "month");
        return month.format("YYYY-MM");
    }

}

export default MonthPicker;
