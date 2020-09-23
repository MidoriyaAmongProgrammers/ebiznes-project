import React from "react";
import axios from "axios";
import StarRatings from "../../../node_modules/react-star-ratings";
import {UserContext} from "../../context/userContext/UserContext";

class ProductPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = { product: {}, reviews: [], rating: 5, review: "",  loading: true, isReviewAlreadyAdded: false};
    this.init = this.init.bind(this);
    this.changeRating = this.changeRating.bind(this);
    this.onReviewChange = this.onReviewChange.bind(this);
    this.addReview = this.addReview.bind(this);
    this.AddReviewComponent = this.AddReviewComponent.bind(this);
    this.init();
  }

  init() {
    const user = this.props.context.userCtx;
    axios
      .get(`http://localhost:9000/api/products/${this.props.match.params.id}`)
      .then((res) => {
        this.setState({
          product: {
            name: res.data.name,
            price: Math.round(res.data.price * 100) / 100,
            description: res.data.description,
          },
        });
      });

    axios.get(`http://localhost:9000/api/reviews/product/${this.props.match.params.id}`).then((res) => {
      this.setState({ reviews: res.data });
    });

    axios
      .get(`http://localhost:9000/api/reviews/user/${this.props.match.params.id}`, 
      { headers: { 'X-Auth-Token': user.token }})
      .then((res) => {
        console.log(res)
        let isAdded = res.data && res.data.length > 0
        
          return this.setState({isReviewAlreadyAdded : isAdded}) 
      });
  }

  onChangeShipment = (e) => {
    console.log(e.target.name);
    this.setState({ shipment: { [e.target.name]: e.target.value } });
  };

  changeRating(newRating, name) {
    this.setState({
      rating: newRating,
    });
  }

  addReview() {
    const { rating, review } = this.state;
    let token = this.props.context.userCtx.token

    axios
      .post("http://localhost:9000/api/reviews", {
        rating,
        content: review,
        user: '',
        id: 0,
        product: parseInt(this.props.match.params.id),
      }, { headers: { 'X-Auth-Token': token }})
      .then((res) => {
        this.init();
      });
  }

  onReviewChange(e) {
    this.setState({ review: e.target.value });
  }

   AddReviewComponent(props){
    const user = this.props.context.userCtx;
    if(!user?.token){
      
      return <h1>Zaloguj się by dodać opinię</h1>
    } else{
      if(this.state.isReviewAlreadyAdded){
      return <h1>Opinia została dodana</h1>
      } else {
        return this.renderAddReviewArea()
      }
    }
  }

  renderAddReviewArea(){
    return(
      <div class="col-6">
      <h1>Ocena</h1>
      <div class="form-group">
        <form>
          <StarRatings
            rating={this.state.rating}
            starRatedColor="gold"
            name="rating"
            changeRating={this.changeRating}
            starDimension="20px"
          />
          <br />
          <label for="exampleFormControlTextarea1">
            Example textarea
          </label>
          <textarea
            class="form-control"
            id="exampleFormControlTextarea1"
            rows="3"
            onChange={this.onReviewChange}
          ></textarea>
          <br />
          <button
            onClick={this.addReview}
            type="button"
            class="btn btn-secondary"
          >
            Add review
          </button>
        </form>
      </div>
    </div>)
  }

  render() {
    return (
      <div class="container">
        <div class="row">
          <div class="col-3">
            <img
              src="https://www.holiday24.com.pl/images/holiday24compl/953-buty-nike-obuwie-damskie-i-m%C4%99skie-silverduck-4092.jpg"
              width="90px"
              height="200px"
              class="card-img-top"
              alt="..."
            ></img>
          </div>
          <div class="col-9">
            <h3>Opis</h3>
            <p>{this.state.product.description}</p>
          </div>
        </div>
        <div class="row">
          <div class="col-6">
            <h1>Opinie</h1>
            {this.state.reviews.map((review) => (
              <div>
                <p>
                  <b>Ocena: </b>
                  <StarRatings
                    rating={review.rating}
                    starRatedColor="gold"
                    name="rating"
                    starDimension="20px"
                  />
                  <br />
                  <b>Opinia: </b>
                  {review.content} <br />
                </p>
              </div>
            ))}
          </div>
          <this.AddReviewComponent/>
        </div>
      </div>
    );
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

export default withContext(ProductPage);
