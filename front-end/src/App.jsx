import "./App.css"
import Home from "./Home/Home.jsx"
import LoginPage from "./LoginPage/LoginPage.jsx"
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";


function App() {


  return (

      <Router>
        <Routes>
          <Route path="/UmPoucoDeTudo/" element={<LoginPage />} />
          <Route path="/UmPoucoDeTudo/home" element={<Home />} />
        </Routes>
      </Router>

  )

}

export default App
