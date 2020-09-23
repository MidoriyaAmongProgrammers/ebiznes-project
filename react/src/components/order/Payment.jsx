import React from 'react';

function Payment(props){
    return (
        <div className="border border-secondary rounded shipment-form">
    <h3 className="text-center">Sposób płatności</h3>
    <div class="container shipment-form">
    <div class="form-group col">
      <select name="method" onChange={props.changeListener} id="inputState" class="form-control">
        <option selected>Wybierz...</option>
        <option>Credit Card</option>
        <option>Blik</option>
      </select>
      <span style={{color: "red"}}>{props.errors["method"]}</span>
    </div>
        
    </div>
    </div>)
}

export default Payment