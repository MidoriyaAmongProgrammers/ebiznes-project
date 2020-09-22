import React from "react";
import axios from "axios";

class Order extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <React.Fragment>
        <div class="card" style= {{width: "18rem"}}>
          <div class="card-header">Zamówienie</div>
          <ul class="list-group list-group-flush">
            <li class="list-group-item">Cras justo odio</li>
            <li class="list-group-item">Dapibus ac facilisis in</li>
            <li class="list-group-item">Vestibulum at eros</li>
          </ul>
        </div>
      </React.Fragment>
    );
  }
}

export default Order;
