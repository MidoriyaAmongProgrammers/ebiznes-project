import React, { useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import queryString from 'query-string';
import {UserContext} from "../../context/userContext/UserContext";

const AuthSuccessfulPage = (props) => {

    const history = useHistory();
    const queryParams = queryString.parse(props.location.search);

    const user = {
        tokenExpiry: parseInt(queryParams.tokenExpiry),
        email: queryParams.email,
    };
    const token = queryParams.token
    

    const userCtx = {
        user:  user,
        token: token
    };

    

    useEffect(() => {
        const performLogIn = async () => {
            localStorage.setItem('userCtx', JSON.stringify(userCtx));
            props.context.setUserCtx(userCtx);
            console.log(userCtx)
            history.push('/')
        };
        performLogIn();
    }, []);

    return <></>
};

const withContext = (Component) => {
    return (props) => 
        <UserContext.Consumer>    
             {(context) => {
                return <Component {...props} context={context} />
             }}
        </UserContext.Consumer>
 }

export default withContext(AuthSuccessfulPage)