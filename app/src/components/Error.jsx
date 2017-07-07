import React from "react";

export default (props) => {
    if (props.error) {
        return (<div className="alert alert-danger">An error happened. Please refresh the page.</div>);
    } else {
        return null;
    }
}
