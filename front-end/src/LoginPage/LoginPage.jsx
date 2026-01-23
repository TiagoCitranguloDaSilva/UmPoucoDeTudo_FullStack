
import "./LoginPage.css"
import React, { useState } from 'react'
import doFetch from "../util/fetchModel"
import { useNavigate } from "react-router-dom"

function LoginPage() {

    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    const navigate = useNavigate()

    const handleSubmit = (e) => {

        e.preventDefault()

        const doRequest = async () => {
            const response = await doFetch(null, "http://localhost:8080/auth/login", "POST", {
                "email": email,
                "password": password
            }, {}, false)

            if (response) {
                localStorage.setItem("userToken", `Bearer ${response}`)
                navigate("/UmPoucoDeTudo")
            }
        }

        doRequest()





    }

    return (
        <div id="loginContainer">
            <div id="loginForm">
                <h1>Login</h1>
                <form autoComplete="off" onSubmit={handleSubmit}>
                    <p><label htmlFor="username">Email: </label><input type="email" name="" id="email" placeholder="exemplo@email.com" required onChange={(e) => setEmail(e.target.value)} /></p>
                    <p><label htmlFor="password">Senha: </label><input type="password" name="" id="password" placeholder="Senha" required onChange={(e) => setPassword(e.target.value)} /></p>
                    <input type="submit" value="Entrar" id="btnLogin" />
                    <p id="msgCadastro">Não tem conta? <a href="register">Cadastre-se</a></p>
                </form>
            </div>
        </div>
    )

}

export default LoginPage;