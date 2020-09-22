import React from 'react';
import "./Basket.css";
import axios from 'axios';

 class BasketProduct extends React.Component{

    populateWithProducts(){
        axios.get(`http://localhost:9000/api/products/${this.props.product.product}`)
      .then(res => {
        this.setState({name: res.data.name, price: (Math.round(res.data.price * 100) / 100), img: res.data.img});
        this.props.calculateProductsCost((Math.round(res.data.price * 100) / 100) * this.props.product.quantity)
        console.log("Product: " + this.props.product.id)
        console.log("Product: " + this.props.product.quantity)
        console.log(res.data)
      })
    }

    constructor(props){
        super(props)
        this.state = {name: 0, price:0, img: '' }
        this.populateWithProducts = this.populateWithProducts.bind(this);
        this.populateWithProducts()
    }

    render(){
    return (
            <div class = "row">
                <div class="col-4">
                    <img src={this.state.img} height="120px" class="card-img" alt="..."></img>
                </div>
                <div class="col-md-8">
                    <div class="card-body">
                        <h5 class="card-title">{this.state.name}</h5>
                        <p className="basket-product-details">
                            <span className="basket-product-details">Price: {this.state.price}</span><br/>
                            <button onClick={() => this.props.onIncrease(this.props.product, this.state.price)} type="button" class="btn btn-success">+</button>
                            <span class="badge badge-secondary quantity">
                                Quantity <span class="badge badge-light quantity">{this.props.product.quantity}</span>
                            </span>
                            <button onClick={() => this.props.onDecrase(this.props.product, this.state.price)} type="button" class="btn btn-danger">-</button>
                            <button onClick={() => this.props.onDelete(this.props.product, this.state.price)} type="button" class="btn btn-danger">Delete</button>
                        </p>
                    </div>
                </div>
        </div>)
    }
}

export default BasketProduct;