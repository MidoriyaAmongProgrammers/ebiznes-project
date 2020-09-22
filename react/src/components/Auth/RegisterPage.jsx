import React from 'react';
import {Link, withRouter } from "react-router-dom";
import axios from 'axios';
import {UserContext} from "../../context/userContext/UserContext";


class RegisterPage extends React.Component{

    onSubmit = (e) => {
        e.preventDefault();
        var validationSucces = this.validateInputs()

        if(validationSucces){
            const { firstName, lastName,  email, password } = this.state;
            
            axios.post('http://localhost:9000/auth/register', { firstName, lastName, email, password })
            .then((res) => {
                console.log(res);
                console.log(res.data);

                const ctx = { user: res.data, token: res.data.token };
                localStorage.setItem('userCtx', JSON.stringify(ctx));
                this.props.context.setUserCtx(ctx);
                console.log("User zapisany w kontekscie")
                console.log(this.props.context.userCtx)
            });
            let history = this.props.history
            history.push("/")
        }
    }

    validateInputs(){
        let errors = {};
        let formIsValid = true;
        this.setState({errors: errors});

        if(this.state.firstName.trim()===""){
            errors["firstName"] = "Cannot be empty"
            formIsValid = false;
        }
        if(this.state.lastName.trim()===""){
            errors["lastName"] = "Cannot be empty"
            formIsValid = false;
        }
        if(this.state.password.trim()===""){
            errors["password"] = "Cannot be empty"
            formIsValid = false;
        }
        if(this.state.email.trim()===""){
            errors["email"] = "Cannot be empty"
            formIsValid = false;
        }

        this.setState({errors: errors});
        return formIsValid;
    }

    constructor() {
        super();
        this.state = {
          firstName: '',
          lastName: '',
          email: '',
          password: '',
          errors: {},
        };
        this.validateInputs = this.validateInputs.bind(this);
      }
    
      onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
      }
    
    render(){
        const { firstName, lastName, password, email } = this.state;
        return (
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-6">
                    <form>
                        <h1 class="text-center">Rejestracja</h1>
                        <div class="form-group">
                            <label>First Name:</label><br/>
                            <input onChange={this.onChange} type="text" class="form-control" name="firstName" value={firstName}/><br/>
                            <span style={{color: "red"}}>{this.state.errors["firstName"]}</span>
                        </div>
                        <div class="form-group">
                            <label>Last Name:</label><br/>
                            <input onChange={this.onChange} type="text" class="form-control" name="lastName" value={lastName}/><br/>
                            <span style={{color: "red"}}>{this.state.errors["lastName"]}</span>
                        </div>
                        <div class="form-group">
                            <label>E-mail:</label><br/>
                            <input onChange={this.onChange} type="text" class="form-control" name="email" value={email}/><br/>
                            <span style={{color: "red"}}>{this.state.errors["email"]}</span>
                        </div>
                        <div class="form-group">
                            <label>Password:</label><br/>
                            <input onChange={this.onChange} type="password" class="form-control" name="password" value={password}/><br/>
                            <span style={{color: "red"}}>{this.state.errors["password"]}</span>
                        </div>
                        <button onClick={this.onSubmit} class="btn btn-primary">Register</button>
                        <h5 class="text-right">
                            Already have account? <Link to="/login">Login</Link>
                        </h5>       
                    </form>
                    </div>
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

export default withRouter(withContext(RegisterPage))