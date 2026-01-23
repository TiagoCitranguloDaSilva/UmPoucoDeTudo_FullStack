
import "./RegisterPage.css"
import React, { useState } from 'react'
import doFetch from "../util/fetchModel"
import { useNavigate } from "react-router-dom"

function LoginPage() {

    const [name, setName] = useState("")
    const [password, setPassword] = useState("")
    const [email, setEmail] = useState("")

    const navigate = useNavigate()

    const handleSubmit = (e) => {

        e.preventDefault()

        const doRequest = async () => {
            const response = await doFetch(null, "http://localhost:8080/auth/register", "POST", {
                "email": email,
                "name": name,
                "password": password
            }, {}, false)

            if (response) {
                navigate("/UmPoucoDeTudo/login")
            }
        }

        doRequest()

    }

    return (
        <div id="registerContainer">
            <div id="registerForm">
                <h1>Cadastro</h1>
                <form autoComplete="off" onSubmit={handleSubmit}>
                    <p><label htmlFor="email">Email: </label><input type="email" name="" id="email" placeholder="exemplo@email.com" required onChange={(e) => setEmail(e.target.value)} /></p>
                    <p><label htmlFor="name">Nome: </label><input type="text" name="" id="name" placeholder="Nome de usuário" required onChange={(e) => setName(e.target.value)} /></p>
                    <p><label htmlFor="password">Senha: </label><input type="password" name="" id="password" placeholder="Senha" required onChange={(e) => setPassword(e.target.value)} /></p>
                    <input type="submit" value="Cadastrar" id="btnRegister" />
                    <p id="msgLogin">Já tem uma conta?<a href="login">Login</a></p>
                </form>
            </div>
        </div>
    )

}

export default LoginPage;