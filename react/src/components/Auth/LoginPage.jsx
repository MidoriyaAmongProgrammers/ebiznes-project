import React from 'react';
import {Link, withRouter} from "react-router-dom";
import axios from 'axios';
import googleIcon from '../../assets/img/google.svg';
import facebookIcon from '../../assets/img/facebook.svg';
import {UserContext} from "../../context/userContext/UserContext";


class LoginPage extends React.Component{

    onSubmit = (e) => {
        e.preventDefault();
        
        var validationSucces = this.validateInputs()

        if(validationSucces){
            const { email, password } = this.state;
            
            axios.post('http://localhost:9000/auth/login', { email, password })
            .then((res) => {
                console.log(res);
                console.log(res.data);
                const ctx = { user: res.data, token: res.data.token };
            localStorage.setItem('userCtx', JSON.stringify(ctx));
            this.props.context.setUserCtx(ctx);
            console.log("User zapisany w kontekscie")
            console.log(this.props.context.userCtx)
            let history = this.props.history
            history.push("/")
            });
        }
    }

    validateInputs(){
        let errors = {};
        let formIsValid = true;
        this.setState({errors: errors});

        if(this.state.email.trim()===""){
            errors["email"] = "Cannot be empty"
            formIsValid = false;
        }
        if(this.state.password.trim()===""){
            errors["password"] = "Cannot be empty"
            formIsValid = false;
        }

        this.setState({errors: errors});
        return formIsValid;
    }

    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
      }


      logWithGoogleOnClick = () =>{
        window.location = 'http://localhost:9000/auth/provider/google';
      }

      logWithFacebookOnClick = () =>{
        window.location = 'http://localhost:9000/auth/provider/facebook';
    }


    constructor(props) {
        super(props);
        this.state = {
          email: '',
          password: '',
          errors: {},
          id : 0
        };
        this.validateInputs = this.validateInputs.bind(this);
        console.log("Props!!!")
        console.log(props)
      }

       

    render(){
        return (
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-6">
                    <form>
                        <h1 class="text-center">Logowanie</h1>
                        <div class="form-group">
                            <label>E-mail:</label><br/>
                            <input onChange={this.onChange} type="text" class="form-control" name="email"/><br/>
                            <span style={{color: "red"}}>{this.state.errors["email"]}</span>
                        </div>
                        <div class="form-group">
                            <label>Password:</label><br/>
                            <input onChange={this.onChange} type="password" class="form-control" name="password"/><br/>
                            <span style={{color: "red"}}>{this.state.errors["password"]}</span>
                        </div>
                        <button onClick={this.onSubmit} type="button" class="btn btn-primary">Login</button>
                        <h5 class="text-right">
                            Not registered? <Link to="/register">Register</Link>
                        </h5>       
                    </form>
                    </div>
                </div>
                <div className="p-2 mt-3">
            <h5>Or use social providers</h5>

            <img src={googleIcon} className="pr-2" style={{ width: "35px", cursor: "pointer" }} onClick={this.logWithGoogleOnClick} />

            <img src={facebookIcon} style={{ width: "25px", cursor: "pointer" }} onClick={this.logWithFacebookOnClick} />
        </div>
            </div>
            )
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
export default withRouter(withContext(LoginPage))