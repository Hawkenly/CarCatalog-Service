import React, {useEffect, useState} from "react"
import axios from "axios";
import {Link, useParams} from "react-router-dom";

export default function Models() {

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
    }, [])

    const loadCar = async ()=>{
        await axios.get(`http://localhost:8080/cars?id=${carId}`)
            .then(function (response){
                console.log(response);
                setCar(response.data);
            })
    };

    const deleteModel = async (carId, modelId) => {
        await axios.delete(`http://localhost:8080/models/delete?id=${modelId}`);
        loadCar();
    }

    return (
        <div className="container">
            <div className="py-4">
                <h2> {car.name}</h2>
                <table className="table shadow">
                    <thead className="table-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">ModelName</th>
                        <th scope="col">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        car.models.map((model, index) => (
                            <tr>
                                <th scope="row" key={index}>{index + 1}</th>
                                <td>{model.modelName}</td>
                                <td>
                                    <Link className="btn btn-outline-dark mx-2" to={`/cars/${carId}/editmodel/${model.id}`}>
                                        Edit
                                    </Link>
                                    <button className="btn btn-danger mx-2"
                                            onClick={() => deleteModel(carId, model.id)}>
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>

                <Link className="btn btn-outline-dark mx-2" to={`/cars/${carId}/addmodel`} >
                    Add model
                </Link>

                <Link className="btn btn-outline-dark mx-2" to={`/editcar/${carId}`}>
                    Back
                </Link>
            </div>
        </div>
    )
}