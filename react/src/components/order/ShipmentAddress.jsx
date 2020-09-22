import React from 'react';

function ShipmentAddress(props){


    return (
        <div className="border border-secondary rounded shipment-form">
    <h3 className="text-center">Shipment Address</h3>
    <div className="container shipment-form">
        <div className="form-row">
            <div className="form-group col-md-6">
                <label for="inputFirstName">First Name</label>
                <input name="fname" onChange={props.changeListener} type="text" className="form-control" id="inputFirstName" placeholder="First name"/>
                <span style={{color: "red"}}>{props.errors["fname"]}</span>
            </div>
            <div className="form-group col-md-6">
                <label for="inputLastName">Last Name</label>
                <input name="lname" onChange={props.changeListener} type="text" className="form-control" id="inputLastName" placeholder="Last name"/>
                <span style={{color: "red"}}>{props.errors["lname"]}</span>
            </div>
        </div>
  <div className="form-group">
    <label for="inputAddress">Address</label>
    <input name="address" onChange={props.changeListener} type="text" className="form-control" id="inputAddress" placeholder="1234 Main St"/>
    <span style={{color: "red"}}>{props.errors["address"]}</span>
  </div>
  <div className="form-row">
    <div className="form-group col-md-6">
      <label for="inputCity">City</label>
      <input name="city" onChange={props.changeListener} type="text" className="form-control" id="inputCity"/>
      <span style={{color: "red"}}>{props.errors["city"]}</span>
    </div>
    <div className="form-group col-md-4">
      <label for="inputZip">Post code</label>
      <input name="postCode" onChange={props.changeListener} type="text" className="form-control" id="inputZip"/>
      <span style={{color: "red"}}>{props.errors["postCode"]}</span>
    </div>
  </div>
    </div>
    </div>)
}

export default ShipmentAddress