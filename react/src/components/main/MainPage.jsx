import React from 'react';
import Products from "./Products.jsx"
import Sidebar from "./Sidebar.jsx"
import axios from 'axios';

class MainPage extends React.Component {

    constructor(props){
        super(props)
        this.state = {products : [], category: 0, subcategory:0}
        this.populateWithProducts()
        this.filterBySubcategory = this.filterBySubcategory.bind(this);
        this.filterByCategory = this.filterByCategory.bind(this);
    }

    populateWithProducts(){
        axios.get(`http://localhost:9000/api/products`)
      .then(res => {
        this.setState({products : res.data});
        console.log(this.state)
      })
    }

    filterBySubcategory(id){
        this.setState({subcategory: id})
        axios.get(`http://localhost:9000/api/products/subcategory/${id}`)
        .then(res => {
            this.setState({products : res.data});
            console.log(this.state)
        })
    }

    filterByCategory(id){
        this.setState({subcategory: id})
        axios.get(`http://localhost:9000/api/products/category/${id}`)
        .then(res => {
            this.setState({products : res.data});
            console.log(this.state)
        })
    }
    

    render(){
        return (
            <div class="container">
                <div class="row">
                    <div class="col-2">
                        <Sidebar filterBySubcategory={this.filterBySubcategory} filterByCategory={this.filterByCategory}/>
                    </div>
                    <div class="col-10">
                        <Products products={this.state.products}/>
                    </div>
                </div>
            </div>
        )
    }
}

export default MainPage;