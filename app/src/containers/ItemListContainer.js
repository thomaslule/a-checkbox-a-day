import { connect } from "react-redux";
import ItemList from "../components/ItemList";
import actions from "../actions";

const mapStateToProps = ( state, ownProps ) => ( {
    items: state.items,
    month: ownProps.month
} );

const mapDispatchToProps = ( dispatch, props ) => {
    return {
        onChangeMonth: ( month ) => dispatch( actions.fetchItems( month ) )
    }
}

export default connect( mapStateToProps, mapDispatchToProps )( ItemList );
