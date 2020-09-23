import React from 'react';
import "./Navbar.css"
import {Link} from 'react-router-dom'
import {UserContext} from "../context/userContext/UserContext";

function Navbar(props){

    function LogInOutNavbarOption(){
        const user = props.context.userCtx;
        if(user?.token){
            return <Link to="/logout"><li>Wyloguj</li></Link>
        } else {
            return <Link to="/login"><li>Zaloguj</li></Link>
        }

    }

    return (
        <nav className="navbar navbar-dark bg-primary">
            <Link className="nav-link navbar-brand" to="/"><li>Strona główna</li></Link>
            <Link to="/basket"><li>Koszyk</li></Link>
            <Link to="/myorders"><li>Moje zamówienia</li></Link>
            <Link to="/profile"><li>Profil użytkownika</li></Link>
            <LogInOutNavbarOption context={props.context}/>
        </nav>
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

export default withContext(Navbar)