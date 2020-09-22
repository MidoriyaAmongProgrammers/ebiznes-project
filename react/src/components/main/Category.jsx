import React from 'react';
import axios from 'axios';


class Category extends React.Component{

    populateWithSubCategories(){
        axios.get(`http://localhost:9000/api/subcategories/category/${this.props.id}`)
      .then(res => {
        this.setState({subcategories : res.data});
        console.log(this.state)
      })
    }

    constructor(props){
        super(props)
        this.state = {subcategories : []}
        this.populateWithSubCategories = this.populateWithSubCategories.bind(this);
        this.populateWithSubCategories()
    }

    render(){
        return (
        <React.Fragment>
            <a className="nav-link category" onClick={() => this.props.filterByCategory(this.props.id)}>{this.props.name}</a>
            <nav className="nav nav-pills flex-column">
                {this.state.subcategories.map(sub => <a className="nav-link ml-3 my-1 category" onClick={() => this.props.filterBySubcategory(sub.id)}>{sub.name}</a>)}
            </nav>
        </React.Fragment>
        )
    }
}


export default Category