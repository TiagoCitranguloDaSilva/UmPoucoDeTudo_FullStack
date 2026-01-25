import { useEffect, useRef, useState } from 'react'
import doFetch from '../util/fetchModel'
import "./EtiquetaForm.css"

function NovaEtiquetaForm({ aoEnviar, idEtiqueta, visivel, fecharForm }) {

    const [nomeEtiqueta, setNomeEtiqueta] = useState("")
    const [dataEtiqueta, setDataEtiqueta] = useState(null)
    const [editBool, setEditBool] = useState(false)
    const [dataEtiquetaFormatada, setDataEtiquetaFormatada] = useState(null)

    const [msgConfirmacaoVisivel, setMsgConfirmacaoVisivel] = useState(false)
    const [btnApagarVisivel, setBtnApagarVisivel] = useState(false)

    const nomeInputRef = useRef()

    useEffect(() => {
        if (idEtiqueta != -1) {
            setEditBool(true)
            setBtnApagarVisivel(true)

            const carregarDados = async () => {
                const responseEtiqueta = await doFetch(`http://localhost:8080/tags/getById/${idEtiqueta}`)
                if (responseEtiqueta.httpStatusCode == 200) {
                    let dados = JSON.parse(responseEtiqueta.data)
                    setNomeEtiqueta(dados.name)
                    setDataEtiqueta(dados.created_at)

                    const date = (dados.created_at).split("-")
                    setDataEtiquetaFormatada(`${date[2]}/${date[1]}/${date[0]}`)
                }
            }

            carregarDados()

        } else {
            setEditBool(false)
            setBtnApagarVisivel(false)
        }
    }, [idEtiqueta])

    useEffect(() => {
        if (visivel) {
            nomeInputRef.current.focus()
        }
    }, [visivel])

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
            const request = await doFetch(rota, metodo, corpo)
            if (request.httpStatusCode == 201 || request.httpStatusCode == 200) {
                fecharFormulario()
                aoEnviar("Etiqueta salva!")
            }
        }

        doRequest()

    }

    const fecharFormulario = () => {
        setNomeEtiqueta("")
        setDataEtiqueta(null)
        fecharForm(false)
    }


    const handleDeleteEtiqueta = async (e) => {
        e.preventDefault()

        const request = await doFetch(`http://localhost:8080/tags/delete/${idEtiqueta}`, "DELETE")
        if (request.httpStatusCode == 200) {
            setMsgConfirmacaoVisivel(false)
            fecharFormulario()
            aoEnviar("Etiqueta apagada!")
        }

    }

    return (
        <div id="formEtiqueta" className={(visivel) ? "" : "hidden"}>
            <form onSubmit={handleSubmit} autoComplete='off' id='formPrincipalEtiqueta'>
                <button onClick={fecharFormulario} id='fecharFormulario' type='button'>Fechar</button>
                <h2>Nova etiqueta</h2>
                <p><label htmlFor="nomeEtiqueta">Nome: </label><input type="text" id="nomeEtiqueta" required placeholder="Nome da etiqueta" value={nomeEtiqueta} maxLength={50} onChange={(e) => { setNomeEtiqueta(e.target.value) }} ref={nomeInputRef} /></p>

                <div className='botoes'>
                    <input type="submit" value="Salvar" />
                    <button className={`buttonApagar ${(btnApagarVisivel) ? "" : "hidden"}`} type='button' onClick={
                        (e) => {
                            e.preventDefault()
                            setMsgConfirmacaoVisivel(true)
                        }
                    }>Apagar</button>

                </div>
                {(dataEtiquetaFormatada != null) ? <div id="dataEtiqueta">Criado em: {dataEtiquetaFormatada}</div> : null}
            </form>
            <div className={`msgConfirmacao ${(msgConfirmacaoVisivel) ? "" : "hidden"}`}>
                <form className='formMsgConfirmacao' onSubmit={(e) => handleDeleteEtiqueta(e)}>
                    <p>Deseja apagar permanentemente esta etiqueta?</p>
                    <div className="botoes">
                        <button type="button" onClick={() => setMsgConfirmacaoVisivel(false)}>Cancelar</button>
                        <input type="submit" value="Apagar" />
                    </div>
                </form>
            </div>
        </div>
    )

}

export default NovaEtiquetaForm