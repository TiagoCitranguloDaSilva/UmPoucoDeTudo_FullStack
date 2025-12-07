import React, { useState, useEffect } from 'react'
import "./HistoriaForm.css"


function HistoriaForm({ aoEnviar, idHistoria }) {

    const [nomeHistoria, setNomeHistoria] = useState("")
    const [historia, setHistoria] = useState("")
    const [escolha, setEscolha] = useState(0)
    const [dataHistoria, setDataHistoria] = useState(null)
    const [etiquetas, setEtiquetas] = useState([])
    const [dataFormatada, setDataFormatada] = useState(null)


    useEffect(() => {

        fetch(`http://localhost:8080/tags/getAll`)
            .then(response => {
                if (!response.ok) {
                    console.log("Erro ao pegar etiquetas")
                }
                return response.json()
            })
            .then(data => {
                setEtiquetas(data)
                setEscolha(data[0].id)
            })

        if (idHistoria != -1) {
            document.querySelector("#formHistoria .buttonApagar").style.display = 'block'

            fetch(`http://localhost:8080/stories/getById/${idHistoria}`)
                .then(response => {
                    if (!response.ok) {
                        console.log("Erro ao pegar dados da história")
                    }
                    return response.json()
                })
                .then(data => {
                    setNomeHistoria(data.title)
                    setHistoria(data.story)
                    setDataHistoria(data.created_at)
                    setEscolha(data.tag.id)

                    const date = (data.created_at).split("-")
                    setDataFormatada(`${date[2]}/${date[1]}/${date[0]}`)
                })

        } else {
            document.querySelector("#formHistoria .buttonApagar").style.display = 'none'
        }
    }, [idHistoria])

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

            let time = new Date()
            let anoAtual = time.getFullYear()
            let mesAtual = time.getMonth() + 1
            let diaAtual = time.getDate()

            let tempo = `${anoAtual}-${mesAtual}-${(diaAtual < 10) ? `0${diaAtual}` : `${diaAtual}`}`

            rota = "http://localhost:8080/stories/new"
            metodo = "POST"
            corpo = {
                "title": nomeHistoria,
                "story": historia,
                "created_at": tempo,
                "tag": { "id": escolha }
            }
        }

        fetch(rota, {
            "method": metodo,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(corpo)
        })
            .then(response => {
                if (!response.ok) {
                    console.log("Erro ao salvar a história")
                }
                aoEnviar("História salva!")
                fecharFormulario()
            })



    }

    const fecharFormulario = () => {
        document.querySelector("#formHistoria").style.display = "none"
        document.body.style.overflowY = "auto"
        setNomeHistoria("")
        setHistoria("")
        setEscolha(0)
        setDataHistoria(null)
    }

    const mostrarConfirmacao = () => {
        if (document.querySelector("#formHistoria .msgConfirmacao").classList.contains("hidden")) {
            document.querySelector("#formHistoria .msgConfirmacao").classList.remove("hidden")
        } else {
            document.querySelector("#formHistoria .msgConfirmacao").classList.add("hidden")
        }
    }

    const handleCancelDelete = () => {
        mostrarConfirmacao()
    }

    const handleDeleteEtiqueta = (e) => {
        e.preventDefault()
    
        fetch(`http://localhost:8080/stories/delete/${idHistoria}`, {
            method: "DELETE"
        })
            .then(response => {
                if(!response.ok){
                    console.log("Erro ao apagar a história!")
                }

                mostrarConfirmacao()
                fecharFormulario()
                aoEnviar("História apagada!")
            })


        
    }

    return (
        <div id="formHistoria">
            <form onSubmit={handleSubmit} autoComplete='off' id='formPrincipalHistoria'>
                <button onClick={fecharFormulario} id='fecharFormulario'>Voltar</button>
                <h2>Nova história</h2>
                <div>
                    <p><label htmlFor="tituloHistoria">Titulo: </label><input type="text" id="tituloHistoria" required placeholder="Nome da história" value={nomeHistoria} onChange={(e) => { setNomeHistoria(e.target.value) }} /></p>
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
                    <button className='buttonApagar' onClick={
                        (e) => {
                            e.preventDefault()
                            mostrarConfirmacao()
                        }
                    }>Apagar</button>

                </div>
                {(dataFormatada != null) ? <div id="dataHistoria">Data de criação: {dataFormatada}</div> : null}
            </form>
            <div className='hidden msgConfirmacao'>
                <form className='formMsgConfirmacao' onSubmit={(e) => handleDeleteEtiqueta(e)}>
                    <p>Deseja apagar permanentemente esta história?</p>
                    <div className="botoes">
                        <button type="button" onClick={handleCancelDelete}>Cancelar</button>
                        <input type="submit" value="Apagar" />
                    </div>
                </form>
            </div>
        </div>
    )

}

export default HistoriaForm