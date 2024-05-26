import React, {useEffect, useState} from "react"
import axios from "axios";
import {Link, useNavigate, useParams} from "react-router-dom";

export default function EditModel() {

    let navigate = useNavigate();

    const {carId,modelId} = useParams();

    const [model, setModel] = useState({
        modelName: ""
    });

    const {modelName} = model;

    const onInputChange = (e) => {
        setModel({...model, [e.target.name]: e.target.value});
    };

    useEffect(() => {
            loadModel();
    }, []);

    const onSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`http://localhost:8080/models/update?id=${modelId}`, model)
                .then(function (response) {
                    console.log(response);
                })
            navigate(`/cars/${carId}/models`);
        } catch (error) {
            console.error("Error:", error);
        }
    };

    const loadModel = async () => {
        await axios.get(`http://localhost:8080/models?id=${modelId}`)
            .then(function (response) {
                console.log(response);
                setModel(response.data);
            });
    };

    return (
        <div className="container">
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">

                    <h2 className="text-center m-4">Edit model</h2>

                    <form onSubmit={(e) => onSubmit(e)}>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                Name
                            </label>
                            <input
                                type={"text"}
                                className="form-control"
                                placeholder="Enter model name"
                                name="modelName"
                                value={modelName}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>

                        <button type="submit" className="btn btn-outline-dark">
                            Submit
                        </button>

                        <Link className="btn btn-outline-danger mx-2" to={`/cars/${carId}/models`}>
                            Cancel
                        </Link>

                    </form>
                </div>

            </div>
        </div>
    )
}