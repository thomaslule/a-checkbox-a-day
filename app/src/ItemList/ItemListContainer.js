import { connect } from 'react-redux';
import ItemList from './ItemList';
import actions from '../actions';

const mapStateToProps = (state, ownProps) => ({
  items: state.itemList,
  month: ownProps.month,
});

const mapDispatchToProps = (dispatch, props) => ({
  onChangeMonth: month => dispatch(actions.fetchItems(month)),
});

export default connect(mapStateToProps, mapDispatchToProps)(ItemList);
