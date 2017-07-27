import { connect } from 'react-redux';
import JournalEntry from './JournalEntry';
import actions from '../actions';

const mapStateToProps = (state, ownProps) => ({
  item: ownProps.item,
});

const mapDispatchToProps = (dispatch, props) => ({
  onChangeText: newText => dispatch(actions.editJournalEntry(props.entry.day, newText)),
});

export default connect(mapStateToProps, mapDispatchToProps)(JournalEntry);
