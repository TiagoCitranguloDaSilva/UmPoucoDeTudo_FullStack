import React, { useState, useEffect } from 'react'
import "./EtiquetaForm.css"
import doFch from '../util/fetchModel'
import { useNavigate } from 'react-router-dom'

function NovaEtiquetaForm({ aoEnviar, idEtiqueta }) {

    const [nomeEtiqueta, setNomeEtiqueta] = useState("")
    const [dataEtiqueta, setDataEtiqueta] = useState(null)
    const [editBool, setEditBool] = useState(false)
    const [dataEtiquetaFormatada, setDataEtiquetaFormatada] = useState(null)

    const navigate = useNavigate()

    useEffect(() => {
        if (idEtiqueta != -1) {
            setEditBool(true)
            document.querySelector(".buttonApagar").style.display = 'block'

            const carregarDados = async () => {
                const responseEtiqueta = await doFetch(navigate, `http://localhost:8080/tags/getById/${idEtiqueta}`)
                if (responseEtiqueta) {
                    setNomeEtiqueta(responseEtiqueta.name)
                    setDataEtiqueta(responseEtiqueta.created_at)

                    const date = (responseEtiqueta.created_at).split("-")
                    setDataEtiquetaFormatada(`${date[2]}/${date[1]}/${date[0]}`)
                }
            }

            carregarDados()

        } else {
            setEditBool(false)
            document.querySelector(".buttonApagar").style.display = 'none'
        }
    }, [idEtiqueta])

    const handleSubmit = (e) => {

        e.preventDefault()

        let corpo;
        let rota;
        let metodo;

        if (editBool) {
            corpo = {
                "id": idEtiqueta,
                "name": nomeEtiqueta,
                "created_at": dataEtiqueta
            }
            rota = "http://localhost:8080/tags/update"
            metodo = "PUT"
        } else {

            corpo = {
                "name": nomeEtiqueta
            }
            rota = "http://localhost:8080/tags/new"
            metodo = "POST"
        }

        const doRequest = async () => {
            const request = await doFetch(navigate, rota, metodo, corpo)
            if (request) {
                fecharFormulario()
                aoEnviar("Etiqueta salva!")
            }
        }

        doRequest()

    }

    const fecharFormulario = () => {
        document.querySelector("#formEtiqueta").style.display = "none"
        document.body.style.overflowY = "auto"
        setNomeEtiqueta("")
        setDataEtiqueta(null)
    }

    const mostrarConfirmacao = () => {
        if (document.querySelector("#formEtiqueta .msgConfirmacao").classList.contains("hidden")) {
            document.querySelector("#formEtiqueta .msgConfirmacao").classList.remove("hidden")
        } else {
            document.querySelector("#formEtiqueta .msgConfirmacao").classList.add("hidden")
        }
    }

    const handleCancelDelete = () => {
        mostrarConfirmacao()
    }

    const handleDeleteEtiqueta = async (e) => {
        e.preventDefault()

        const request = await doFetch(navigate, `http://localhost:8080/tags/delete/${idEtiqueta}`, "DELETE")
        if (request) {
            mostrarConfirmacao()
            fecharFormulario()
            aoEnviar("Etiqueta apagada!")
        }

    }

    return (
        <div id="formEtiqueta">
            <form onSubmit={handleSubmit} autoComplete='off' id='formPrincipalEtiqueta'>
                <button onClick={fecharFormulario} id='fecharFormulario' type='button'>Fechar</button>
                <h2>Nova etiqueta</h2>
                <p><label htmlFor="nomeEtiqueta">Nome: </label><input type="text" id="nomeEtiqueta" required placeholder="Nome da etiqueta" value={nomeEtiqueta} maxLength={50} onChange={(e) => { setNomeEtiqueta(e.target.value) }} /></p>

                <div className='botoes'>
                    <input type="submit" value="Salvar" />
                    <button className='buttonApagar' type='button' onClick={
                        (e) => {
                            e.preventDefault()
                            mostrarConfirmacao()
                        }
                    }>Apagar</button>

                </div>
                {(dataEtiquetaFormatada != null) ? <div id="dataEtiqueta">Criado em: {dataEtiquetaFormatada}</div> : null}
            </form>
            <div className='hidden msgConfirmacao'>
                <form className='formMsgConfirmacao' onSubmit={(e) => handleDeleteEtiqueta(e)}>
                    <p>Deseja apagar permanentemente esta etiqueta?</p>
                    <div className="botoes">
                        <button type="button" onClick={handleCancelDelete}>Cancelar</button>
                        <input type="submit" value="Apagar" />
                    </div>
                </form>
            </div>
        </div>
    )

}

export default NovaEtiquetaForm