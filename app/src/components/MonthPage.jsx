import React from "react";
import moment from "moment";
import {
    Redirect
} from "react-router-dom";
import ItemListContainer from "../containers/ItemListContainer";
import MonthPicker from "./MonthPicker";

class MonthPage extends React.Component {

    render() {
        if ( this.isMonthValid( this.props.match.params.month ) ) {
            return (
                <div>
                    <MonthPicker month={this.props.match.params.month} />
                    <ItemListContainer month={this.props.match.params.month} />
                </div> );
        } else {
            return ( <Redirect to="/" /> );
        }
    }
    
    isMonthValid( month ) {
        return moment( month, "YYYY-MM", true ).isValid();
    }
}

export default MonthPage;
