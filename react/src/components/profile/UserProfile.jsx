import React from 'react';
import {UserContext} from "../../context/userContext/UserContext";
import axios from 'axios';


class UserProfile extends React.Component{

    constructor(props) {
        super(props);
        this.state = {user : {}};
        this.getProfileData = this.getProfileData.bind(this);
        this.UserProfileComponent = this.UserProfileComponent.bind(this);
      }

    getProfileData(user){
        axios.get(`http://localhost:9000/api/users/token`, 
      { headers: { 'X-Auth-Token': user.token }})
      .then((res) => {
          this.setState({user: res.data})
          console.log(res)
      });
    }

    UserProfileComponent(){
        const user = this.props.context.userCtx;
        if(user?.token){
            this.getProfileData(user)
            return (<React.Fragment>
                <h1>Imię: {this.state.user.firstName} </h1>
                <h1>Nazwisko: {this.state.user.lastName} </h1>
                <h1>E-mail: {this.state.user.email} </h1>
            </React.Fragment>)
        } else {
            return <h1>Musisz być zalogowany</h1>
        }
    }

    render(){ return <this.UserProfileComponent/>
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

export default withContext(UserProfile)