import React from 'react';
import ShipmentAddress from "./ShipmentAddress"
import ShipmentProvider from "./ShipmentProvider"
import Payment from "./Payment"
import "./Order.css";
import {UserContext} from "../../context/userContext/UserContext";
import axios from 'axios';
import {withRouter} from "react-router-dom";



class OrderPage extends React.Component{


    onChangePayment = (e) => {
        console.log(e.target.name)
        this.setState({ payment: {...this.state.payment, [e.target.name]: e.target.value }});
    }

    onChangeShipment = (e) => {
        console.log(e.target.name)
        this.setState({ shipment: {...this.state.shipment,  [e.target.name]: e.target.value }});
    }

    validateInputs(){
        let errors = {};
        let formIsValid = true;
        this.setState({errors: errors});

        if(this.state.shipment.email?.trim()===""){
            errors["email"] = "Cannot be empty"
            formIsValid = false;
        }
        if(this.state.shipment.password?.trim()===""){
            errors["password"] = "Cannot be empty"
            formIsValid = false;
        }
        if(this.state.shipment.fname?.trim()===""){
            errors["fname"] = "Cannot be empty"
            formIsValid = false;
        }
        if(this.state.shipment.lname?.trim()===""){
            errors["lname"] = "Cannot be empty"
            formIsValid = false;
        }
        if(this.state.shipment.address?.trim()===""){
            errors["address"] = "Cannot be empty"
            formIsValid = false;
        }
        if(this.state.shipment.city?.trim()===""){
            errors["city"] = "Cannot be empty"
            formIsValid = false;
        }
        if(this.state.shipment.provider?.trim()===""){
            errors["provider"] = "Cannot be empty"
            formIsValid = false;
        }
        if(this.state.shipment.postCode?.trim()===""){
            errors["postCode"] = "Cannot be empty"
            formIsValid = false;
        }
        if(this.state.payment.method?.trim()===""){
            errors["method"] = "Cannot be empty"
            formIsValid = false;
        }

        this.setState({errors: errors});
        return formIsValid;
    }

    submitOrder(){
        let validation = this.validateInputs()
            if(!validation){
                return
            }
        console.log(this.props.location.state)
        const { shipment, payment } = this.state;
        let paymentId;
        let userId;
        let token = this.props.context.userCtx.token
        console.log(this.state)
        let history = this.props.history
        history.push("/")
         axios.post(`http://localhost:9000/api/payments`, {
            value: this.props.location.state.value,
            paid: true,
            method: payment.method,
            id: 0
        }).then(res => {
            console.log("Response payment:")
            console.log(res)
            console.log("Payment id")
            paymentId = res.data.id
            console.log(paymentId)
        

        axios.get(`http://localhost:9000/api/users/token`, {
                headers: { 'X-Auth-Token': token }
        }).then(res => {
            console.log("Response usera:")
            console.log(res)
            userId = res.data.id
        

        axios.post(`http://localhost:9000/api/shipmentAddresses`, {
            address: shipment.address,
            city: shipment.city,
            postCode: shipment.postCode,
            id: 0
        })
      .then(res => {

        console.log("Response adresu:")
        console.log(res)

        axios.post(`http://localhost:9000/api/shipments`, {
            id: 0,
            date: '',
            address: res.data.id,
            firstName: shipment.fname,
            lastName: shipment.lname,
            provider: shipment.provider
        }).then(res => {
            console.log("Response dostawy:")
            console.log(res)

            axios.post(`http://localhost:9000/api/orders`, {
            id: 0,
            date: '',
            coupon: this.props.location.state.coupon,
            shipment: res.data.id,
            payment: paymentId,
            user: userId
        }).then(res => {
            console.log("Response orderu:")
            console.log(res)
        })
        })
      }) 
    })
})

axios.delete(`http://localhost:9000/api/basketproducts/user`, {
                headers: { 'X-Auth-Token': token }
        }).then(res => {
            console.log("Response basket:")
            console.log(res)
        })
    }

    constructor(props) {
        super(props);
        this.state = {
          payment: {value: 0, paid : false, method: ''},
          shipment: {fname: '', lname: '', address: '', city: '', postCode: '', provider: '' },
          errors: {},
          id : 0
        };
        this.submitOrder = this.submitOrder.bind(this);
    }

    render(){
        return (
        <div class="container">
            <div class="row">
                <div class="col-6">
                <React.Fragment>
            <h1>Szczegóły zamówienia</h1>
            <form className="border border-secondary rounded summary">
                <div class="form-group">
                    <h2>Podsumowanie</h2>
                    <p>Koszt produktów: <b>{this.props.location.state.productsCost}</b></p>
                    <p>Wartość kuponu: <b>{this.props.location.state.couponsValue}</b></p>
                    <p>Łączny koszt: <b>{this.props.location.state.value}</b></p> 
                </div>
            </form>
        </React.Fragment>
                </div>
                <div class="col-6">
                    <ShipmentAddress changeListener={this.onChangeShipment} errors={this.state.errors}/>
                </div>
            </div>
            <div class="row">
                <div class="col-6">
                    <Payment changeListener={this.onChangePayment} errors={this.state.errors}/>
                </div>
                <div class="col-6">
                    <ShipmentProvider changeListener={this.onChangeShipment} errors={this.state.errors}/>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <button onClick={this.submitOrder} type="button" class="btn btn-primary btn-lg btn-block submit-order">Złóż zamówienie</button>
                </div>
            </div>
        </div>)
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


export default withRouter(withContext(OrderPage))