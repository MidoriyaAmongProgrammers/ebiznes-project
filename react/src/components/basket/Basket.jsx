import React from 'react';
import BasketProducts from "./BasketProducts.jsx"
import BasketDetails from "./BasketDetails.jsx"

class Basket extends React.Component{

    constructor(props){
        super(props)
        this.state = {productsCost : 0, totalCost: 0, couponValue: 0 }
        this.calculateTotalCost = this.calculateTotalCost.bind(this);
        this.increaseProductsCost = this.increaseProductsCost.bind(this);
        this.decreaseProductsCost = this.decreaseProductsCost.bind(this);
        this.setCouponsValue = this.setCouponsValue.bind(this);
    }

    calculateTotalCost(){
        this.setState({ totalCost: this.round((this.state.productsCost - this.state.couponValue))})
    }

    round(number){
        return (Math.round(number * 100) / 100)
    }

    increaseProductsCost(cost){
        this.number = this.number + 1
        this.setState({productsCost: this.round((this.state.productsCost + cost)) }, () => this.calculateTotalCost())
        console.log("Jestem w dodawaniu ceny. Koszt dodany: " + cost)
        console.log(this.state)
    }

    decreaseProductsCost(cost){
        this.setState({productsCost: this.round((this.state.productsCost - cost))}, () => this.calculateTotalCost() )
        this.calculateTotalCost()
    }

    setCouponsValue(value){
        this.setState({couponValue: value }, () => this.calculateTotalCost())
    }



    render(){
        return (
        <div class="container">
            <div class="row">
                <div class="col-6">
                    <BasketProducts increaseProductsCost={this.increaseProductsCost} decreaseProductsCost={this.decreaseProductsCost}/>
                </div>
                <div class="col-6">
                    <BasketDetails totalCost={this.state.totalCost} productsCost={this.state.productsCost} 
                    couponsValue={this.state.couponValue}  setCoupon={this.setCouponsValue} />
                </div>
            </div>
        </div>)
    }
}

export default Basket