import { connect } from 'react-redux';
import Item from './Item';
import actions from '../actions';

const mapStateToProps = (state, ownProps) => ({
  item: ownProps.item,
});

const mapDispatchToProps = (dispatch, props) => ({
  onCancel: () => dispatch(actions.cancelItem(props.item.id)),
  onMove: newMonth => dispatch(actions.moveItem(props.item.id, newMonth)),
  onRestore: () => dispatch(actions.restoreItem(props.item.id)),
  onDelete: () => dispatch(actions.deleteItem(props.item.id)),
  onChangeCompleteTask: () => {
    if (props.item.completed) {
      dispatch(actions.uncompleteTask(props.item.id));
    } else {
      dispatch(actions.completeTask(props.item.id));
    }
  },
  onChangeText: newText => dispatch(actions.changeItemText(props.item.id, newText)),
});

export default connect(mapStateToProps, mapDispatchToProps)(Item);
