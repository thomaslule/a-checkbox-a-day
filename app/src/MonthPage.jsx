import React from "react";
import TaskList from "./TaskList";
import MonthPicker from "./MonthPicker";

class MonthPage extends React.Component {

    render() {
        return (
            <div>
                <MonthPicker month={this.props.month} />
                <TaskList month={this.props.month} />
            </div>
        );
    }

}

export default MonthPage;
