import { connect } from 'react-redux';
import Journal from './Journal';
import actions from '../actions';

const mapStateToProps = (state, ownProps) => ({
  journal: state.journal,
  month: ownProps.month,
});

const mapDispatchToProps = (dispatch, props) => ({
  onChangeMonth: month => dispatch(actions.fetchJournal(month)),
});

export default connect(mapStateToProps, mapDispatchToProps)(Journal);
