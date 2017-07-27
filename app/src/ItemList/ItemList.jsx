import React from 'react';
import ItemContainer from '../Item/ItemContainer';
import NewItemContainer from '../NewItem/NewItemContainer';

class ItemList extends React.Component {
  componentDidMount() {
    this.props.onChangeMonth(this.props.month);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.month !== this.props.month) {
      this.props.onChangeMonth(nextProps.month);
    }
  }

  render() {
    const itemList = this.props.items.map(item =>
      (<ItemContainer
        key={item.id}
        item={item}
      />));
    return (
      <ul className="list-unstyled">
        {itemList}
        <NewItemContainer
          month={this.props.month}
        />
      </ul>
    );
  }
}

export default ItemList;
