import { connect } from "react-redux";
import Item from "../components/Item";
import actions from "../actions";

const mapStateToProps = ( state, ownProps ) => ( {
    item: ownProps.item
} );

const mapDispatchToProps = ( dispatch, props ) => {
    return {
        onCancel: () => dispatch( actions.cancelItem( props.item.id ) ),
        onChangeCompleteTask: ( newVal, id ) => {
            if ( newVal ) {
                dispatch( actions.completeTask( id ) );
            } else {
                dispatch( actions.uncompleteTask( id ) );
            }
        }
    }
}

export default connect( mapStateToProps, mapDispatchToProps )( Item );
