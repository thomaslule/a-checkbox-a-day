import React from 'react';
import moment from 'moment';

import EditableText from './EditableText';

export default props => (
  <li className="journal-entry">
    <time dateTime="{moment(props.entry.day, 'YYYY-MM-DD').format()}">{moment(props.entry.day, 'YYYY-MM-DD').format('dddd D')}</time>
    <EditableText text={props.entry.text} onChangeText={text => props.onChangeText(text)} />
  </li>);
