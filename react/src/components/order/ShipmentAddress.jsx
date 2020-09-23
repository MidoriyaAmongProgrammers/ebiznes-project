import React from 'react';

function ShipmentAddress(props){


    return (
        <div className="border border-secondary rounded shipment-form">
    <h3 className="text-center">Adres dostawy</h3>
    <div className="container shipment-form">
        <div className="form-row">
            <div className="form-group col-md-6">
                <label for="inputFirstName">Imię</label>
                <input name="fname" onChange={props.changeListener} type="text" className="form-control" id="inputFirstName" placeholder="Imię"/>
                <span style={{color: "red"}}>{props.errors["fname"]}</span>
            </div>
            <div className="form-group col-md-6">
                <label for="inputLastName">Nazwisko</label>
                <input name="lname" onChange={props.changeListener} type="text" className="form-control" id="inputLastName" placeholder="Nazwisko"/>
                <span style={{color: "red"}}>{props.errors["lname"]}</span>
            </div>
        </div>
  <div className="form-group">
    <label for="inputAddress">Adres</label>
    <input name="address" onChange={props.changeListener} type="text" className="form-control" id="inputAddress" placeholder="Ulica"/>
    <span style={{color: "red"}}>{props.errors["address"]}</span>
  </div>
  <div className="form-row">
    <div className="form-group col-md-6">
      <label for="inputCity">Miasto</label>
      <input name="city" onChange={props.changeListener} type="text" className="form-control" id="inputCity"/>
      <span style={{color: "red"}}>{props.errors["city"]}</span>
    </div>
    <div className="form-group col-md-4">
      <label for="inputZip">Kod pocztowy</label>
      <input name="postCode" onChange={props.changeListener} type="text" className="form-control" id="inputZip"/>
      <span style={{color: "red"}}>{props.errors["postCode"]}</span>
    </div>
  </div>
    </div>
    </div>)
}

export default ShipmentAddress