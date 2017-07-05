import { connect } from "react-redux";
import TaskList from "../components/TaskList";
import actions from "../actions";

const mapStateToProps = ( state ) => ( {
    tasks: state.tasks,
    month: state.month
} );

const mapDispatchToProps = ( dispatch, props ) => {
    return {
        onInit: ( month ) => dispatch( actions.fetchTasks( month ) ),
        onAddTask: ( todo ) => dispatch( actions.addTask( { todo, month: props.month } ) ),
        onCompleteTask: ( id ) => dispatch( actions.completeTask( id ) )
    }
}

export default connect( mapStateToProps, mapDispatchToProps )( TaskList );
