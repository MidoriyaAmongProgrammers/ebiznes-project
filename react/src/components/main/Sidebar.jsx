import React from 'react';
import Category from "./Category"
import axios from 'axios';

class Sidebar extends React.Component{

    populateWithCategories(){
        axios.get(`http://localhost:9000/api/categories`)
      .then(res => {
        this.setState({categories : res.data});
        console.log(this.state)
      })
    }

    constructor(props){
        super(props)
        this.state = {categories : []}
        this.populateWithCategories()
    }

    render(){
        return (
            <nav id="navbar-example3" className="navbar navbar-light bg-light">
                <a class="navbar-brand" href="#">Categories</a>
                <nav class="nav nav-pills flex-column">
                    {this.state.categories.map(category => <Category name={category.name} id={category.id}
                    filterBySubcategory={this.props.filterBySubcategory} filterByCategory={this.props.filterByCategory}/>)}
                </nav>
            </nav>
            
        )
    }
}

export default Sidebar