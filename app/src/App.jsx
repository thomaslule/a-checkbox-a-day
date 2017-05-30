import React from "react";
import {
    BrowserRouter as Router,
    Route,
    Redirect,
    Switch
} from "react-router-dom"
import moment from "moment";
import MonthPage from "./MonthPage";

import "./App.css";
import "bootstrap/dist/css/bootstrap.css";

class App extends React.Component {

    render() {
        return (
            <div>
                <nav className="navbar navbar-default navbar-static-top">
                    <div className="container">
                        <span className="navbar-brand">A checkbox a day</span>
                    </div>
                </nav>
                <div className="container">
                    <Router>
                        <Switch>
                            <Route
                                exact path="/month/:month"
                                render={(props) => {
                                    if (this.isMonthValid(props.match.params.month)) {
                                        return (<MonthPage month={props.match.params.month} />);
                                    } else {
                                        return (<Redirect to={this.defaultPage()} />);
                                    }
                                }}
                            />
                            <Redirect from="*" to={this.defaultPage()} />
                        </Switch>
                    </Router>
                </div>
            </div>
        );
    }

    isMonthValid(month) {
        return moment(month, "YYYY-MM", true).isValid();
    }

    defaultPage() {
        return "/month/" + moment().format( "YYYY-MM" );
    }

}

export default App;
