import { connect } from "react-redux";
import Error from "../components/Error";

const mapStateToProps = ( state ) => ( {
    error: state.error
} );

export default connect( mapStateToProps )( Error );
