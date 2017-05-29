import React from "react";
import { Link } from "react-router-dom";

class MonthPicker extends React.Component {
    
    render() {
        return (
            <div className="row">
                <h1 className="col-xs-12 text-center">
                    <Link to="/2017-05">&larr;</Link>
                    <span>{this.props.match.params.month}</span>
                    <Link to="/2017-06">&rarr;</Link>
                </h1>
            </div>
        );
    }

}

export default MonthPicker;
