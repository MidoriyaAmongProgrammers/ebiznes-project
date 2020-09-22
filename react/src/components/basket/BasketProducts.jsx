import React from "react";
import BasketProduct from "./BasketProduct.jsx";
import axios from "axios";
import { UserContext } from "../../context/userContext/UserContext";

class BasketProducts extends React.Component {
  populateWithProducts() {
    var token = this.props.context.userCtx.token;
    console.log("Token:");
    console.log(token);

    axios
      .get(`http://localhost:9000/api/basketproducts/user`, {
        headers: { "X-Auth-Token": token },
      })
      .then((res) => {
        console.log("Dane zalogowania:");
        console.log(this.props.context.userCtx.token);

        console.log("Response:");
        console.log(res);
        this.setState({ products: res.data });
        console.log("Produkty w koszyku: ");
        console.log(this.state);
      });
  }

  constructor(props) {
    super(props);
    this.state = { products: [] };
    this.deleteProductFromBasket = this.deleteProductFromBasket.bind(this);
    this.decraseQuantity = this.decraseQuantity.bind(this);
    this.increaseQuantity = this.increaseQuantity.bind(this);
    this.increaseInDatabase = this.increaseInDatabase.bind(this);
    this.decreaseInDatabase = this.decreaseInDatabase.bind(this);
    this.populateWithProducts();
  }

  decraseQuantity(product, price) {
      if(product.quantity - 1 <= 0){
          this.deleteProductFromBasket(product, price)
          return
      }
    const products = [...this.state.products];
    const index = this.state.products.indexOf(product);
    products[index] = { ...product };
    products[index].quantity = product.quantity - 1;
    this.setState({ products: products });
    this.props.decreaseProductsCost(price);
    this.decreaseInDatabase(product);
    console.log("Po: " + JSON.stringify(this.state));
  }

  increaseQuantity(product, price) {
    const products = [...this.state.products];
    const index = this.state.products.indexOf(product);
    products[index] = { ...product };
    products[index].quantity = product.quantity + 1;
    this.setState({ products: products });
    this.props.increaseProductsCost(price);
    this.increaseInDatabase(product);
    console.log(this.state);
    console.log("Po: " + JSON.stringify(this.state));
  }

  increaseInDatabase(product) {
    var token = this.props.context.userCtx.token;
    console.log("Id produktu wysłanego do koszyka; " + product.product);
    console.log("Token:" + token);

    axios
      .post(
        `http://localhost:9000/api/basketproducts`,
        { product: product.product },
        {
          headers: { "X-Auth-Token": token },
        }
      )
      .then((res) => {
        console.log(res);
        console.log(res.data);
      });
  }

  decreaseInDatabase(product) {
    var token = this.props.context.userCtx.token;
    console.log("Id produktu wysłanego do koszyka; " + product.product);
    console.log("Token:" + token);

    axios
      .post(
        `http://localhost:9000/api/basketproducts/decrease`,
        { product: product.product },
        {
          headers: { "X-Auth-Token": token },
        }
      )
      .then((res) => {
        console.log(res);
        console.log(res.data);
      });
  }

  decrase(product) {
    return { ...product, quantity: product.quantity - 1 };
  }

  increase(product) {
    return { ...product, quantity: product.quantity + 1 };
  }

  deleteProductFromBasket(productToDelete, cost) {
    console.log(productToDelete.id);
    console.log("Przed: " + JSON.stringify(this.state));
    this.setState({
      products: this.state.products.filter((product) => product.id !== productToDelete.id)
    }, () => console.log(this.state));
    axios
      .delete(`http://localhost:9000/api/basketproducts/${productToDelete.id}`)
      .then((res) => {
        console.log(this.state);
      });
      this.props.decreaseProductsCost(productToDelete.quantity * cost)
    console.log("Po: " + JSON.stringify(this.state));
  }

  render() {
    return (
      <React.Fragment>
        <h1>Basket products</h1>
        {this.state.products.map((p) => (
          <BasketProduct
            product={p}
            onDelete={this.deleteProductFromBasket}
            onDecrase={this.decraseQuantity}
            onIncrease={this.increaseQuantity}
            calculateProductsCost={this.props.increaseProductsCost}
          />
        ))}
      </React.Fragment>
    );
  }
}

const withContext = (Component) => {
  return (props) => (
    <UserContext.Consumer>
      {(context) => {
        return <Component {...props} context={context} />;
      }}
    </UserContext.Consumer>
  );
};

export default withContext(BasketProducts);
