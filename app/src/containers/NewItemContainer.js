import { connect } from 'react-redux';
import NewItem from '../components/NewItem';
import actions from '../actions';

const mapStateToProps = (state, ownProps) => ({
});

const mapDispatchToProps = (dispatch, props) => ({
  onAddItem: (itemType, text) => dispatch(actions.addItem({ itemType, text, month: props.month })),
});

export default connect(mapStateToProps, mapDispatchToProps)(NewItem);
