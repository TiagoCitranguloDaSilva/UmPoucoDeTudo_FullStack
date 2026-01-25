
import { useState } from 'react'
import { useNavigate } from "react-router-dom"
import Mensagem from '../Mensagem/Mensagem'
import doFetch from "../util/fetchModel"
import "./RegisterPage.css"

function LoginPage() {

    const [name, setName] = useState("")
    const [password, setPassword] = useState("")
    const [email, setEmail] = useState("")

    const [textoMensagem, setTextoMensagem] = useState(null)
    const [mensagemVisivel, setMensagemVisivel] = useState(false)
    const [mensagemFalha, setMensagemFalha] = useState(false)


    const navigate = useNavigate()

    const handleSubmit = (e) => {

        e.preventDefault()

        const doRequest = async () => {
            const response = await doFetch("http://localhost:8080/auth/register", "POST", {
                "email": email,
                "name": name,
                "password": password
            }, {}, false)

            if (response.httpStatusCode == 201) {
                const mensagem = [response.data, false]
                sessionStorage.setItem("mensagem", JSON.stringify(mensagem))
                navigate("/UmPoucoDeTudo/login")
            } else {
                setTextoMensagem(response.data)
                setMensagemFalha(response.failed)
                setMensagemVisivel(true)
            }
        }

        doRequest()

    }

    const colherEstado = (estado) => {

        if (estado) {
            setMensagemVisivel(false)
            setTextoMensagem(null)
        }

    }

    return (
        <div id="registerContainer">
            <Mensagem texto={textoMensagem} visivel={mensagemVisivel} falha={mensagemFalha} estado={colherEstado} />
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