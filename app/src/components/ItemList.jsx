import React from "react";
import ItemContainer from "../containers/ItemContainer";
import NewItemContainer from "../containers/NewItemContainer";

class ItemList extends React.Component {

    componentDidMount() {
        this.props.onChangeMonth( this.props.month );
    }

    componentWillReceiveProps( nextProps ) {
        if ( nextProps.month !== this.props.month ) {
            this.props.onChangeMonth( nextProps.month );
        }
    }

    render() {
        let itemList = this.props.items.map(( item ) =>
            <ItemContainer
                key={item.id}
                item={item}
            /> );
        return (
            <div className="row">
                <div className="col-xs-offset-4 col-xs-4">
                    <ul className="list-unstyled">
                        {itemList}
                        <NewItemContainer
                            month={this.props.month}
                        />
                    </ul>
                </div>

            </div>
        );
    }
}

export default ItemList;
