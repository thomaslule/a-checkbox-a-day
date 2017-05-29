import React from "react";

class MonthPicker extends React.Component {

    render() {
        return (
            <div className="row">
                <h1 className="col-xs-12 text-center">
                    <a href="#" className="previous">&larr;</a>
                    <span>Mai 2017</span>
                    <a href="#" className="next">&rarr;</a>
                </h1>
            </div>
        );
    }

}

export default MonthPicker;
