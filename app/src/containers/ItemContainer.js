import { connect } from "react-redux";
import Item from "../components/Item";
import actions from "../actions";

const mapStateToProps = ( state, ownProps ) => ( {
    item: ownProps.item
} );

const mapDispatchToProps = ( dispatch, props ) => {
    return {
        onCancel: () => dispatch( actions.cancelItem( props.item.id ) ),
        onRestore: () => dispatch( actions.restoreItem( props.item.id ) ),
        onChangeCompleteTask: () => {
            if ( props.item.completed ) {
                dispatch( actions.uncompleteTask( props.item.id ) );
            } else {
                dispatch( actions.completeTask( props.item.id ) );
            }
        }
    }
}

export default connect( mapStateToProps, mapDispatchToProps )( Item );
