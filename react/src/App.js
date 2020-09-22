import React from "react";
import Navbar from "./components/Navbar";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import LoginPage from "./components/Auth/LoginPage";
import LogOutComponent from "./components/Auth/LogOutComponent";
import RegisterPage from "./components/Auth/RegisterPage";
import AuthSuccessfulPage from "./components/Auth/AuthSuccessfulPage";
import MainPage from "./components/main/MainPage";
import Basket from "./components/basket/Basket";
import OrderPage from "./components/order/OrderPage";
import ProductPage from "./components/product/ProductPage";
import UserContextProvider from "./context/userContext/UserContextProvider";
import MyOrdersPage from "./components/myOrders/MyOrdersPage";


function App() {
  return (
    <Router>
      <UserContextProvider>
        <div>
          <Navbar />
        </div>
        <Switch>
          <Route path="/login" exact component={LoginPage} />
          <Route path="/logout" exact component={LogOutComponent} />
          <Route path="/register" exact component={RegisterPage} />
          <Route path="/basket" exact component={Basket} />
          <Route path="/myorders" exact component={MyOrdersPage} />
          <Route path="/order" component={OrderPage} />
          <Route path="/product/:id" component={ProductPage} />
          <Route path="/" exact component={MainPage} />
          <Route path="/auth/successful" component={AuthSuccessfulPage} />
        </Switch>
      </UserContextProvider>
    </Router>
  );
}

export default App;
