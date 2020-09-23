import React from "react";
import axios from "axios";

class Order extends React.Component {
  constructor(props) {
    super(props);
    this.state = { payment: {}, coupon: 0, shipment: {}, shipmentAddress: {} };
    this.init = this.init.bind(this);
    this.init()
  }

  init(){
    axios
      .get(`http://localhost:9000/api/payments/${this.props.order.payment}`)
      .then((res) => {
        console.log("Response:");
        console.log(res);
        this.setState({ payment: res.data });
        console.log("State: ");
        console.log(this.state);
      });
      axios
      .get(`http://localhost:9000/api/shipments/${this.props.order.shipment}`)
      .then((res) => {
        console.log("Response:");
        console.log(res);
        this.setState({ shipment: res.data });
        axios
      .get(`http://localhost:9000/api/shipmentAddresses/${res.data.address}`)
      .then((res) => {
        console.log("Response:");
        console.log(res);
        this.setState({ shipmentAddress: res.data });
        console.log("State: ");
        console.log(this.state);
      });
      });
      if(this.props.order.coupon != 0 && this.props.order.coupon != null){
      axios
      .get(`http://localhost:9000/api/coupons/${this.props.order.coupon}`)
      .then((res) => {
        console.log("Response:");
        console.log(res);
        this.setState({ coupon: res.data });
        console.log("State: ");
        console.log(this.state);
      });
    }
  }

  isPaid(){
    if(this.state.payment.paid == true){
      return "Tak"
    } else {
      return "Nie"
    }
  }

  round(number){
    return (Math.round(number * 100) / 100)
}

checkCoupon(){
  if(this.state.coupon == 0){
    return "Brak kuponu"
  } else {
    return this.state.coupon.name
  }
}

  render() {
    return (
      <React.Fragment>
        <tr>
      <th scope="row">{this.props.order.id}</th>
    <td>{this.round(this.state.payment.value)}</td>
      <td>{this.checkCoupon()}</td>
    <td>{this.state.shipment.firstName} {this.state.shipment.lastName}</td>
      <td>{this.state.shipment.firstName}</td>
      <td>{this.state.payment.method}</td>
    <td>{this.isPaid()}</td>
    <td>{this.state.shipmentAddress.postCode} {this.state.shipmentAddress.city} {this.state.shipmentAddress.address}</td>
    </tr>
      </React.Fragment>
    );
  }
}

export default Order;
