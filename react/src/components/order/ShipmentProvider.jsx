import React from 'react';

function ShipmentProvider(props){
    return (
        <div className="border border-secondary rounded shipment-form">
    <h3 className="text-center">Shipment Provider</h3>
    <div class="container shipment-form">
    <div class="form-group col">
      <select name="provider" onChange={props.changeListener} id="inputState" class="form-control">
        <option selected>Choose...</option>
        <option>UPS</option>
        <option>Polish Post</option>
        <option>Inpost</option>
      </select>
      <span style={{color: "red"}}>{props.errors["provider"]}</span>
    </div>
        
    </div>
    </div>)
}

export default ShipmentProvider