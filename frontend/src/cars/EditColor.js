import React, {useEffect, useState} from "react"
import axios from "axios";
import {Link, useNavigate, useParams} from "react-router-dom";

export default function EditColor() {

    let navigate = useNavigate();

    const {carId,colorId} = useParams();

    const [color, setColor] = useState({
        colorName: ""
    });

    const {colorName} = color;

    const onInputChange = (e) => {
        setColor({...color, [e.target.name]: e.target.value});
    };

    useEffect(() => {
        loadColor();
    }, []);

    const onSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`http://localhost:8080/colors/update?id=${colorId}`, color)
                .then(function (response) {
                    console.log(response);
                })
            navigate(`/cars/${carId}/colors`);
        }catch (error) {
            console.error("Error:", error);
        }
    };

    const loadColor = async () => {
        await axios.get(`http://localhost:8080/colors?id=${colorId}`)
            .then(function (response) {
                console.log(response);
                setColor(response.data);
            });
    };

    return (
        <div className="container">
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">

                    <h2 className="text-center m-4">Edit color</h2>

                    <form onSubmit={(e) => onSubmit(e)}>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                Name
                            </label>
                            <input
                                type={"text"}
                                className="form-control"
                                placeholder="Enter color name"
                                name="colorName"
                                value={colorName}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>

                        <button type="submit" className="btn btn-outline-dark">
                            Submit
                        </button>

                        <Link className="btn btn-outline-danger mx-2" to={`/cars/${carId}/colors`}>
                            Cancel
                        </Link>

                    </form>
                </div>

            </div>
        </div>
    )
}