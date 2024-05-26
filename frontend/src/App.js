import "./App.css";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import Navbar from "./layout/Navbar";
import Home from "./pages/Home";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import AddCar from "./cars/AddCar"
import EditCar from "./cars/EditCar";
import ViewCar from "./cars/ViewCar";
import Models from "./cars/Models";
import EditModel from "./cars/EditModel";
import AddModel from "./cars/AddModel";
import Colors from "./cars/Colors";
import AddColor from "./cars/AddColor"
import EditColor from "./cars/EditColor"

function App() {
    return (
    <div className="App">
        <Router>
            <Navbar />
            <Routes>
                <Route exact path="/" element={<Home/>}/>
                <Route exact path="/addcar" element={<AddCar/>}/>
                <Route exact path="/editcar/:carId" element={<EditCar/>}/>
                <Route exact path="/viewcar/:carId" element={<ViewCar/>}/>
                <Route exact path="/cars/:carId/models" element={<Models/>}/>
                <Route exact path="/cars/:carId/addmodel" element={<AddModel/>}/>
                <Route exact path="/cars/:carId/editmodel/:modelId" element={<EditModel/>}/>
                <Route exact path="/cars/:carId/colors" element={<Colors/>}/>
                <Route exact path="/cars/:carId/addcolor" element={<AddColor/>}/>
                <Route exact path="/cars/:carId/editcolor/:colorId" element={<EditColor/>}/>
            </Routes>
        </Router>
    </div>
  );
}

export default App;
