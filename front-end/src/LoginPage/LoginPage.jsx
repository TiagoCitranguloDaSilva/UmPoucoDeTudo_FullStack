
import { useEffect, useState } from 'react'
import { useNavigate } from "react-router-dom"
import Mensagem from "../Mensagem/Mensagem.jsx"
import doFetch from "../util/fetchModel"
import "./LoginPage.css"

function LoginPage() {

    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    const [textoMensagem, setTextoMensagem] = useState(null)
    const [mensagemVisivel, setMensagemVisivel] = useState(false)
    const [mensagemFalha, setMensagemFalha] = useState(false)

    const navigate = useNavigate()

    useEffect(() => {
        let mensagem = sessionStorage.getItem("mensagem")
        if (mensagem) {
            sessionStorage.setItem("mensagem", "")
            let mensagemJson = JSON.parse(mensagem)
            mostrarMensagem(mensagemJson[0], mensagemJson[1])
            if (mensagemJson[1]) {
                localStorage.setItem("userToken", "")
            }
        } else if (localStorage.getItem("userToken")) {
            const doResponse = async () => {
                const response = await doFetch("http://localhost:8080/auth/validate", "GET", null, null, true)
                if (response.httpStatusCode == 200) {
                    navigate("/UmPoucoDeTudo")
                } else {
                    localStorage.setItem("userToken", "")
                }
            }

            doResponse()
        }

    }, [])

    const handleSubmit = (e) => {

        e.preventDefault()

        const doRequest = async () => {
            const response = await doFetch("http://localhost:8080/auth/login", "POST", {
                "email": email,
                "password": password
            }, {}, false)

            if (response.httpStatusCode == 200) {
                localStorage.setItem("userToken", `Bearer ${response.data}`)
                sessionStorage.setItem("mensagem", JSON.stringify(["Bem vindo de volta!", false]))
                navigate("/UmPoucoDeTudo")
            } else {
                mostrarMensagem(response.data, response.failed)
            }
        }

        doRequest()

    }

    const mostrarMensagem = (mensagem = "", failed) => {
        if (mensagem) {
            setTextoMensagem(mensagem)
            setMensagemFalha(failed)
            setMensagemVisivel(true)
        }
    }

    const coletarEstadoMensagem = (estado) => {
        if (estado) {
            setMensagemVisivel(false)
            setTextoMensagem(null)
        }
    }

    return (
        <div id="loginContainer">
            <Mensagem texto={textoMensagem} visivel={mensagemVisivel} estado={coletarEstadoMensagem} falha={mensagemFalha} />
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