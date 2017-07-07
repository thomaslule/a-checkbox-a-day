import React from "react";
import {
    BrowserRouter as Router,
    Route,
    Redirect,
    Switch
} from "react-router-dom";
import moment from "moment";
import MonthPage from "./components/MonthPage";
import ErrorContainer from "./containers/ErrorContainer";

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
                <ErrorContainer />
                <div className="container">
                    <Router>
                        <Switch>
                            <Route
                                exact path="/month/:month"
                                component={MonthPage}
                            />
                            <Redirect from="*" to={this.defaultPage()} />
                        </Switch>
                    </Router>
                </div>
            </div>
        );
    }

    defaultPage() {
        return "/month/" + moment().format( "YYYY-MM" );
    }
    
}

export default App;
