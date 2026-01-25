import { useEffect, useRef, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import doFetch from '../util/fetchModel'
import "./HistoriaForm.css"

function HistoriaForm({ aoEnviar, idHistoria, visivel, fecharForm }) {

    const [nomeHistoria, setNomeHistoria] = useState("")
    const [historia, setHistoria] = useState("")
    const [escolha, setEscolha] = useState(0)
    const [dataHistoria, setDataHistoria] = useState(null)
    const [etiquetas, setEtiquetas] = useState([])
    const [dataFormatada, setDataFormatada] = useState(null)

    const [mensagemConfirmacaoVisivel, setMensagemConfirmacaoVisivel] = useState(false)

    const navigate = useNavigate()

    const btnApagarVisivel = useRef(false)
    const inputTitulo = useRef()

    useEffect(() => {

        if (!visivel) return;

        inputTitulo.current.focus()

        const doRequest = async () => {
            const request = await doFetch(`http://localhost:8080/tags/getAll`)
            if (request.httpStatusCode == 200) {
                let etiquetasJson = JSON.parse(request.data)
                setEtiquetas(etiquetasJson)
                setEscolha(etiquetasJson[0].id)
            }
        }

        doRequest()

        if (idHistoria != -1) {

            const doRequest = async () => {
                const request = await doFetch(`http://localhost:8080/stories/getById/${idHistoria}`)
                if (request.httpStatusCode == 200) {
                    let historiaJson = JSON.parse(request.data)
                    setNomeHistoria(historiaJson.title)
                    setHistoria(historiaJson.story)
                    setDataHistoria(historiaJson.created_at)
                    setEscolha(historiaJson.tag.id)

                    const date = (historiaJson.created_at).split("-")
                    setDataFormatada(`${date[2]}/${date[1]}/${date[0]}`)
                }
            }

            doRequest()

            if (btnApagarVisivel.current.classList.contains('hidden')) {
                btnApagarVisivel.current.classList.remove('hidden')
            }

        } else {
            if (!btnApagarVisivel.current.classList.contains('hidden')) {
                btnApagarVisivel.current.classList.add('hidden')
            }
        }
    }, [idHistoria, visivel])

    const handleSubmit = (e) => {
        e.preventDefault()

        let rota
        let metodo
        let corpo

        if (idHistoria != -1) {
            rota = "http://localhost:8080/stories/update"
            metodo = "PUT"
            corpo = {
                "id": idHistoria,
                "title": nomeHistoria,
                "story": historia,
                "created_at": dataHistoria,
                "tag": { "id": escolha }
            }
        } else {

            rota = "http://localhost:8080/stories/new"
            metodo = "POST"
            corpo = {
                "title": nomeHistoria,
                "story": historia,
                "tag": { "id": escolha }
            }
        }

        const doRequest = async () => {
            const request = await doFetch(rota, metodo, corpo)
            if (request.httpStatusCode == 201 || request.httpStatusCode == 200) {
                fecharFormulario()
                aoEnviar("História salva!")
            }
        }

        doRequest()

    }

    const fecharFormulario = () => {
        setNomeHistoria("")
        setHistoria("")
        setEscolha(0)
        setDataHistoria(null)
        fecharForm(false)
    }

    const handleDeleteEtiqueta = async (e) => {
        e.preventDefault()

        const request = await doFetch(`http://localhost:8080/stories/delete/${idHistoria}`, "DELETE")
        if (request.httpStatusCode == 200) {
            setMensagemConfirmacaoVisivel(false)
            fecharFormulario()
            aoEnviar("História apagada!")
        }

    }

    return (
        <div id="formHistoria" className={(visivel) ? "" : "hidden"}>
            <form onSubmit={handleSubmit} autoComplete='off' id='formPrincipalHistoria'>
                <button onClick={fecharFormulario} id='fecharFormulario' type='button'>Voltar</button>
                <h2>Nova história</h2>
                <div>
                    <p><label htmlFor="tituloHistoria">Titulo: </label><input type="text" id="tituloHistoria" required placeholder="Nome da história" value={nomeHistoria} onChange={(e) => { setNomeHistoria(e.target.value) }} ref={inputTitulo} /></p>
                    <p>
                        <label htmlFor="escolhaEtiqueta">Etiqueta: </label>
                        <select id="escolhaEtiqueta" onChange={(e) => setEscolha(e.target.value)} value={escolha}>
                            {etiquetas.map((etiqueta) => (
                                <option value={etiqueta.id} key={etiqueta.id}>{etiqueta.name}</option>
                            ))}
                        </select>
                    </p>
                </div>
                <p><label htmlFor="historiaTextArea">História: </label><textarea id="historiaTextArea" onChange={(e) => setHistoria(e.target.value)} value={historia} placeholder='Conte sua história...'></textarea></p>
                <div className='botoes'>
                    <input type="submit" value="Salvar" />
                    <button className='buttonApagar' ref={btnApagarVisivel} onClick={
                        (e) => {
                            e.preventDefault()
                            setMensagemConfirmacaoVisivel(true)
                        }
                    }>Apagar</button>

                </div>
                {(dataFormatada != null) ? <div id="dataHistoria">Data de criação: {dataFormatada}</div> : null}
            </form>
            <div className={(mensagemConfirmacaoVisivel) ? 'msgConfirmacao' : 'hidden msgConfirmacao'}>
                <form className='formMsgConfirmacao' onSubmit={(e) => handleDeleteEtiqueta(e)}>
                    <p>Deseja apagar permanentemente esta história?</p>
                    <div className="botoes">
                        <button type="button" onClick={(e) => setMensagemConfirmacaoVisivel(false)}>Cancelar</button>
                        <input type="submit" value="Apagar" />
                    </div>
                </form>
            </div>
        </div>
    )

}

export default HistoriaForm