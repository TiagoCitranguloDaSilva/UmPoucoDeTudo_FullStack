import "./App.css"
import Home from "./Home/Home.jsx"
import LoginPage from "./LoginPage/LoginPage.jsx"
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";


function App() {


  return (

      <Router>
        <Routes>
          <Route path="/UmPoucoDeTudo/login" element={<LoginPage />} />
          <Route path="/UmPoucoDeTudo" element={<Home />} />
        </Routes>
      </Router>

  )

}

export default App
