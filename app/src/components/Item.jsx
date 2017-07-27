import React from 'react';
import EditableText from './EditableText';
import './Item.css';

class Item extends React.Component {
  getItemClass() {
    const classes = ['item'];
    classes.push(this.props.item.itemType.toLowerCase());
    if (this.props.item.cancelled) classes.push('cancelled');
    if (this.props.item.completed) classes.push('completed');
    return classes.join(' ');
  }

  handleCancel(e) {
    this.props.onCancel();
    e.preventDefault();
  }

  handleRestore(e) {
    this.props.onRestore();
    e.preventDefault();
  }

  handleDelete(e) {
    this.props.onDelete();
    e.preventDefault();
  }

  render() {
    const checkbox = this.props.item.itemType === 'TASK' ? (
      <div className="checkbox">
        <input
          type="checkbox"
          checked={this.props.item.completed}
          onChange={() => this.props.onChangeCompleteTask()}
        />
      </div>) : null;
    const text = this.props.item.cancelled ?
      (<span className="item-name">{this.props.item.text}</span>) :
      (<EditableText
        className="item-name"
        text={this.props.item.text}
        onChangeText={newText => this.props.onChangeText(newText)}
      />);
    const buttonCancel = this.props.item.cancelled ? null :
      (<a href="" title="Cancel" onClick={e => this.handleCancel(e)}>
        <i className="glyphicon glyphicon-remove" /></a>);
    const buttonRestore = this.props.item.cancelled ?
      (<a href="" title="Restore" onClick={e => this.handleRestore(e)}>
        <i className="glyphicon glyphicon-asterisk" /></a>) : null;
    const buttonDelete = this.props.item.cancelled ?
      (<a href="" title="Delete" onClick={e => this.handleDelete(e)}>
        <i className="glyphicon glyphicon-trash" /></a>) : null;
    return (
      <li className={this.getItemClass()}>
        {checkbox}
        {text}
        <span className="action-buttons">{buttonCancel}{buttonRestore}{buttonDelete}</span>
      </li>);
  }
}

export default Item;
