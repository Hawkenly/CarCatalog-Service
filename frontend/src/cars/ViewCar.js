import React, {useEffect, useState} from "react"
import {Link, useParams} from "react-router-dom";
import axios from "axios";

export default function ViewCar() {

    const [car, setCar] = useState({
        name:"",
        popular:"",
        country:"",
        models:[
            {
                modelName:""
            }
        ],
        colors:[
            {
                colorName:""
            }
        ]
    });

    const {carId} = useParams();

    useEffect(() => {
        loadCar();
    }, []);

    const loadCar = async () => {
        await axios.get(`http://localhost:8080/cars?id=${carId}`)
            .then(function (response){
                console.log(response);
                setCar(response.data);
            })
    }


    return (
        <div className="container">
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">

                    <h2 className="text-center m-3">Car details</h2>
                    <h3 className="text-center m-3">{car.name}</h3>

                    <div className="card mb-3">
                        <div className="card-header">
                            Details of car id: {carId}
                            <ul className="list-group list-group-flush">
                                <li className="list-group-item">
                                    <b>Name:</b>
                                    {car.name}
                                </li>

                                <li className="list-group-item">
                                    <b>IsPopular:</b>
                                    {car.popular}
                                </li>

                                <li className="list-group-item">
                                    <b>Country:</b>
                                    {car.country}
                                </li>
                            </ul>
                        </div>
                    </div>

                    <div className="card mb-3">
                        <div className="card-header">
                            Car models
                            <ul className="list-group list-group-flush">
                                <li className="list-group-item">
                                    <b>Models: </b>
                                    {
                                        car.models.map(model => (
                                            <text>
                                                {model.modelName},
                                            </text>
                                        ))
                                    }
                                </li>
                            </ul>
                        </div>
                    </div>

                    <div className="card mb-3">
                        <div className="card-header">
                            Car colors
                            <ul className="list-group list-group-flush">
                                <li className="list-group-item">
                                    <b>Colors: </b>
                                    {
                                        car.colors.map(color => (
                                            <text>
                                                {color.colorName},
                                            </text>
                                        ))
                                    }
                                </li>
                            </ul>
                        </div>
                    </div>


                    <Link className="btn btn-outline-dark my-2" to="/">
                        Back to Home page
                    </Link>

                </div>
            </div>
        </div>
    );
}