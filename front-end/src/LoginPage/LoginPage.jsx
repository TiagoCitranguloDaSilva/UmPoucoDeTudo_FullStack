
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
            mensagem = JSON.parse(mensagem)
            sessionStorage.setItem("mensagem", "")
            setTextoMensagem(mensagem[0])
            setMensagemFalha(mensagem[1])
            setMensagemVisivel(true)
        }
    }, [])

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