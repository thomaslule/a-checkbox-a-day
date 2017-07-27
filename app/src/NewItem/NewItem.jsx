import React from 'react';

class NewItem extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      text: '',
      itemType: 'TASK',
    };
  }

  handleChangeText(event) {
    this.setState(Object.assign({}, this.state, { text: event.target.value }));
  }

  handleChangeType(e, itemType) {
    this.setState(Object.assign({}, this.state, { itemType }));
    e.preventDefault();
  }

  handleSubmit(e) {
    this.props.onAddItem(this.state.itemType, this.state.text);
    this.setState({ text: '' });
    e.preventDefault();
  }

  render() {
    const typeLabel = {
      TASK: 'Task',
      EVENT: 'Event',
      NOTE: 'Note',
    };

    const dropdownButtons = Object.keys(typeLabel).map(itemType =>
      (<li key={itemType}><a onClick={e => this.handleChangeType(e, itemType)} href="">{typeLabel[itemType]}</a></li>));

    return (
      <li className="newItem">
        <form onSubmit={e => this.handleSubmit(e)}>
          <div className="input-group">
            <span className="input-group-btn">
              <button type="button" className="btn btn-default dropdown-toggle" data-toggle="dropdown">
                {typeLabel[this.state.itemType]} <span className="caret" />
              </button>
              <ul className="dropdown-menu">{dropdownButtons}</ul>
            </span>
            <input
              type="text"
              required="required"
              className="form-control"
              value={this.state.text}
              onChange={e => this.handleChangeText(e)}
            />
            <span className="input-group-btn">
              <button type="submit" className="btn">Add Item</button>
            </span>
          </div>
        </form>
      </li>);
  }
}

export default NewItem;
