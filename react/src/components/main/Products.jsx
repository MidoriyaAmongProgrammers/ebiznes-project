import React from 'react';
import Product from "./Product.jsx"

class Products extends React.Component{
    

    constructor(props){
        super(props)
    }


    render(){
        return (
        <React.Fragment>
            <h1>Produkty</h1>
            <div className="card-group">
            {this.props.products.map(product => <Product product={product} />)}
            </div>
        </React.Fragment>
        )
    }
}

export default Products;