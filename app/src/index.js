import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';
import createSagaMiddleware from 'redux-saga';

import App from './App/App';
import reducer from './reducers';
import saga from './sagas';

import registerServiceWorker from './registerServiceWorker';

import './index.css';

const sagaMiddleware = createSagaMiddleware();
const store = createStore(reducer, applyMiddleware(sagaMiddleware));
sagaMiddleware.run(saga);

ReactDOM.render(React.createElement(
  Provider,
  { store },
  React.createElement(App, null),
), document.getElementById('root'));

registerServiceWorker();
