import React from 'react';
import axios from 'axios';
import Order from "./Order.jsx"
import {UserContext} from "../../context/userContext/UserContext";

class MyOrdersPage extends React.Component {

    constructor(props){
        super(props)
        this.state = {orders : []}
        this.populateWithOrders = this.populateWithOrders.bind(this);
        this.populateWithOrders()
    }

    populateWithOrders(){
        var token = this.props.context.userCtx.token;
        axios.get(`http://localhost:9000/api/orders/token`, {
            headers: { 'X-Auth-Token': token }})
      .then(res => {
        this.setState({orders : res.data});
        console.log(this.state)
      })
    }

    

    render(){
        return (
        <React.Fragment>
            <h1>My orders</h1>
            <div className="card-group">
            {this.state.orders.map(order => <Order order={order} />)}
            </div>
        </React.Fragment>
        )
    }
}

const withContext = (Component) => {
    return (props) => 
        <UserContext.Consumer>    
             {(context) => {
                return <Component {...props} context={context} />
             }}
        </UserContext.Consumer>
 }

export default withContext(MyOrdersPage);