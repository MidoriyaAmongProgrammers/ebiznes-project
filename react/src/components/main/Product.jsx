import React from 'react';
import "./Product.css";
import axios from 'axios';
import {UserContext} from "../../context/userContext/UserContext";
import {Link} from 'react-router-dom'


function Product(props){


    function addProductToBasket(){
        var token = props.context.userCtx.token;
        console.log("Id produktu wysłanego do koszyka; " + props.product.id)
        console.log("Token:" + token);

            axios.post(`http://localhost:9000/api/basketproducts`, {product: props.product.id},{
                headers: { 'X-Auth-Token': token }
            })
            .then(res => {
            console.log(res);
            console.log(res.data);
            })
            alert("Dodano do koszyka!")
    }


    function round(number){
        return (Math.round(number * 100) / 100)
    }

    function RenderAddToBasketButton(props){
        const user = props.context.userCtx;
        if(user?.token){
           return( <div><button type="button" onClick={addProductToBasket} class="btn btn-primary">Add to cart</button>
           <Link to="/product/1"> <button type="button" onClick={addProductToBasket} class="btn btn-primary">See details</button></Link></div>)
        } else {
            return <center><Link to="/product/1"> <button type="button" onClick={addProductToBasket} class="btn btn-primary">See details</button></Link></center>
        }
    }
    
    return (
    <div className="col-4">
        <div className="card" style={{margin: "10px"}}>
            <img src={props.product.img}  width="90px" height="200px" class="card-img-top" alt="..."></img>
            <div class="card-body">
                <h5 class="card-title">{props.product.name}</h5>
                <ul>
                    <li className="product-details">Price: {round(props.product.price)}</li>
                    <li className="product-details">Description: {props.product.description}</li>
                </ul>
                <RenderAddToBasketButton context={props.context}/>
            </div>
        </div>
    </div>)
}

const withContext = (Component) => {
    return (props) => 
        <UserContext.Consumer>    
             {(context) => {
                return <Component {...props} context={context} />
             }}
        </UserContext.Consumer>
 }

export default withContext(Product);