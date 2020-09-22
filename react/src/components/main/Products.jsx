import React from 'react';
import Product from "./Product.jsx"
import axios from 'axios';

class Products extends React.Component{
    

    constructor(props){
        super(props)
    }


    render(){
        return (
        <React.Fragment>
            <h1>Products</h1>
            <div className="card-group">
            {this.props.products.map(product => <Product product={product} />)}
            </div>
        </React.Fragment>
        )
    }
}

export default Products;