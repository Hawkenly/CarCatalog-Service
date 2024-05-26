import React, {useState} from "react"
import axios from "axios";
import {Link, useNavigate, useParams} from "react-router-dom";

export default function AddCar() {

    let navigate = useNavigate()

    const [color, setColor] = useState({
        colorName:""
    });

    const {carId} = useParams();

    const {colorName} = color;

    const onInputChange = (e) => {
        setColor({...color, [e.target.name]: e.target.value})
    };

    const onSubmit = async (e) => {
        e.preventDefault();
        try {
            const result = await axios.post("http://localhost:8080/colors/add", color)
            await axios.post(`http://localhost:8080/cars/${carId}/colors/${result.data.id}/add`)
                .then(function (response) {
                    console.log(response);
                });
            navigate(`/cars/${carId}/colors`);
        }catch (error){
            console.error("Error: ", error);
        }
    };

    return (
        <div className="container">
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">

                    <h2 className="text-center m-4">Add new color</h2>

                    <form onSubmit={(e) => onSubmit(e)}>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                Name
                            </label>
                            <input
                                type={"text"}
                                className="form-control"
                                placeholder="Enter color name "
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