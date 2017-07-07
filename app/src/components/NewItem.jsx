import React from "react";

class NewItem extends React.Component {

    constructor( props ) {
        super( props );
        this.state = {
            text: ""
        };
    }

    render() {
        return (
            <li>
                <div className="input-group">
                    <input
                        type="text"
                        required="required"
                        className="form-control"
                        value={this.state.text}
                        onChange={( e ) => this.handleChange( e )}
                    />
                    <span className="input-group-btn">
                        <button onClick={() => this.handleClickAdd()} className="btn">Add Item</button>
                    </span>
                </div>
            </li> );
    }

    handleChange( event ) {
        this.setState( { text: event.target.value } );
    }

    handleClickAdd() {
        this.props.onAddItem( this.state.text );
        this.setState( { text: "" } );
    }

}

export default NewItem;
