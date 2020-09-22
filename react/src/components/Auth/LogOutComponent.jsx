import React, { useState, useEffect } from 'react';
import {Link, useHistory} from "react-router-dom";
import {UserContext} from "../../context/userContext/UserContext";

function LogOutComponent(props){

    let history = useHistory();


    useEffect(() => {
        const user = {
            user:  null,
            token: null
        };
        localStorage.setItem('userCtx', JSON.stringify(user));
        props.context.setUserCtx(user);
        history.push('/')

    });

    return (
        <div></div>
    )
}

const withContext = (Component) => {
    return (props) => 
        <UserContext.Consumer>    
             {(context) => {
                return <Component {...props} context={context} />
             }}
        </UserContext.Consumer>
 }

export default withContext(LogOutComponent)