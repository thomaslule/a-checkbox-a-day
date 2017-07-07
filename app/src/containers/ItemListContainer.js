import { connect } from "react-redux";
import ItemList from "../components/ItemList";
import actions from "../actions";

const mapStateToProps = ( state, ownProps ) => ( {
    items: state.items,
    month: ownProps.month
} );

const mapDispatchToProps = ( dispatch, props ) => {
    return {
        onNewMonth: ( month ) => dispatch( actions.fetchItems( month ) ),
        onAddItem: ( itemType, text ) => dispatch( actions.addItem( { itemType, text, month: props.month } ) ),
        onCompleteTask: ( id ) => dispatch( actions.completeTask( id ) )
    }
}

export default connect( mapStateToProps, mapDispatchToProps )( ItemList );
