
import "./LoginPage.css"
import React, { useState } from 'react'
import doFetch from "../util/fetchModel"
import { useNavigate } from "react-router-dom"

function LoginPage() {

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")

    const navigate = useNavigate()

    const handleSubmit = (e) => {

        e.preventDefault()

        const doRequest = async () => {
            const response = await doFetch(null, "http://localhost:8080/auth/login", "POST", {
                "username": username,
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
                    <p><label htmlFor="username">Nome de usuário: </label><input type="text" name="" id="username" placeholder="Nome de usuário" required onChange={(e) => setUsername(e.target.value)} /></p>
                    <p><label htmlFor="password">Senha: </label><input type="password" name="" id="password" placeholder="Senha" required onChange={(e) => setPassword(e.target.value)} /></p>
                    <input type="submit" value="Entrar" id="btnLogin" />
                    <p id="msgCadastro">Não tem conta? <a href="">Cadastre-se</a></p>
                </form>
            </div>
        </div>
    )

}

export default LoginPage;