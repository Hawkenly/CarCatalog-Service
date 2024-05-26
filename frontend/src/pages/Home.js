import React, {useEffect, useState} from "react"
import axios from "axios";
import {Link} from "react-router-dom";

export default function Home() {

    const [cars, setCars] = useState([]);

    useEffect(() => {
        loadCars();
    }, [])

    const loadCars=async ()=>{
        const result =await axios.get("http://localhost:8080/cars/");
        setCars(result.data);
    }

    const deleteCar = async (carId) => {
        await axios.delete(`http://localhost:8080/cars/delete?id=${carId}`);
        loadCars();
    }

    return (
        <div className="container">
            <div className="py-4">
                <table className="table shadow">
                    <thead className="table-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name</th>
                        <th scope="col">Is popular?</th>
                        <th scope="col">Country</th>
                        <th scope="col">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        cars.map((car, index) => (
                            <tr>
                                <th scope="row" key={index}>{index + 1}</th>
                                <td>{car.name}</td>
                                <td>{car.popular}</td>
                                <td>{car.country}</td>
                                <td>
                                    <Link className="btn btn-dark mx-2" to={`/viewcar/${car.id}`}>
                                        View
                                    </Link>

                                    <Link className="btn btn-outline-dark mx-2" to={`/editcar/${car.id}`}>
                                        Edit
                                    </Link>

                                    <button className="btn btn-danger mx-2"
                                            onClick={() => deleteCar(car.id)}>
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </div>
        </div>
    )
}