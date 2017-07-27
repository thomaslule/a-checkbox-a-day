import { combineReducers } from 'redux';
import itemList from './ItemList/itemListReducer';
import journal from './Journal/journalReducer';
import error from './Error/errorReducer';

const log = (state = {}, action) => { console.log('action', action); return state; };

const root = combineReducers({
  log,
  itemList,
  journal,
  error,
});

export default root;
