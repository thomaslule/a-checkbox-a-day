import { connect } from "react-redux";
import MonthPicker from "../components/MonthPicker";
import actions from "../actions";

const mapStateToProps = ( state ) => ( {
    month: state.month
} );

const mapDispatchToProps = ( dispatch, props ) => {
    return {
        // onChangeMonth: ( month ) => dispatch( actions.changeMonth( month ) )
    }
}

export default connect( mapStateToProps, mapDispatchToProps )( MonthPicker );
