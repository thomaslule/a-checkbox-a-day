import React from "react";
import ContentEditable from "react-contenteditable";

class EditableText extends React.Component {

    render() {
        return (
            <ContentEditable tagName="span" className={this.props.className} html={this.props.text} onKeyDown={( e ) => this.handleTextKeyDown( e )} onBlur={( e ) => this.handleChangeText( e )} />
        );
    }

    handleChangeText( e ) {
        if ( e.target.innerText !== this.props.text ) {
            this.props.onChangeText( e.target.innerText );
        }
    }

    handleTextKeyDown( e ) {
        if ( e.keyCode === 27 ) { // ESCAPE
            e.target.innerText = this.props.text;
            e.target.blur();
            e.preventDefault();
        } else if ( e.keyCode === 13 ) { // ENTER
            e.target.blur();
            e.preventDefault();
        }
    }

}

export default EditableText;
