import { useNavigate } from "react-router-dom"
import logoutIcon from "../assets/logoutIcon.png"
import "./LogoutButton.css"

function LogoutButton() {

    const navigate = useNavigate()

    const handleClick = () => {
        localStorage.setItem("userToken", "")
        sessionStorage.setItem("mensagem", JSON.stringify(["Você saiu da conta!", false]))
        navigate("/UmPoucoDeTudo/Login")
    }

    return (
        <button type="button" id="logoutButton" onClick={handleClick}>
            <img src={logoutIcon} />
        </button>
    )
}

export default LogoutButton;