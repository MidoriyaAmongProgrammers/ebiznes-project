import React from "react";
import { Link } from "react-router-dom";
import axios from "axios";

class BasketDetails extends React.Component {
  constructor(props) {
    super(props);
    this.state = { coupon: "", couponId: 0 };
    this.checkCoupon = this.checkCoupon.bind(this);
  }

  onChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  checkCoupon() {
    axios
      .get(`http://localhost:9000/api/coupons/code/${this.state.coupon}`)
      .then((res) => {
        this.props.setCoupon(res.data.value);
        this.setState({ couponId: res.data.id });
        console.log(res.data.value);
      });
  }

  render() {
    return (
      <React.Fragment>
        <h1>Basket details</h1>
        <form className="border border-secondary rounded summary">
          <div class="form-group">
            <h2>Summary</h2>
            <p>Products cost: {this.props.productsCost}</p>
            <p>Coupons value: {this.props.couponsValue}</p>
            <p>Total cost: {this.props.totalCost}</p>
          </div>
          <Link
            to={{
              pathname: "/order",
              state: {
                coupon: this.state.couponId,
                value: this.props.totalCost,
                productsCost: this.props.productsCost,
                couponsValue: this.props.couponsValue,
              },
            }}
            className="summary-button"
          >
            <button type="submit" class="btn btn-lg btn-block basket-submit">
              Go further
            </button>
          </Link>
        </form>
        <form className="align-self-end">
          <div class="form-group">
            <label htmlFor="exampleInputEmail1">Coupon</label>
            <input
              onChange={this.onChange}
              name="coupon"
              type="text"
              class="form-control"
              placeholder="Enter Coupon"
            />
            <small class="form-text text-muted">
              You can iput here your discount code
            </small>
          </div>
          <button
            onClick={this.checkCoupon}
            type="button"
            class="btn btn-primary"
          >
            Submit
          </button>
        </form>
      </React.Fragment>
    );
  }
}

export default BasketDetails;
