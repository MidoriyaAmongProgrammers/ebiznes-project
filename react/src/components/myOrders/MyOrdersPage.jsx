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
            <center><h1>Zamówienia</h1></center><br></br>
            <table class="table">
  <thead class="thead-dark">
    <tr>
      <th scope="col">#</th>
      <th scope="col">Koszt</th>
      <th scope="col">Kupon</th>
      <th scope="col">Imię i naziwsko</th>
      <th scope="col">Sposób dostawy</th>
      <th scope="col">Sposób płatności</th>
      <th scope="col">Opłacone</th>
      <th scope="col">Adres</th>
    </tr>
  </thead>
  <tbody>
  {this.state.orders.map(order => <Order order={order} />)}
  </tbody>
</table>
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