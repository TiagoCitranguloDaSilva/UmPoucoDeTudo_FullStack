import React, { useState, useEffect } from 'react'
import "./HistoriaForm.css"


function HistoriaForm({aoEnviar, editarHistoria, aoClicar, etiquetas}){

    const [nomeHistoria, setNomeHistoria] = useState("")
    const [historia, setHistoria] = useState("")
    const [escolha, setEscolha] = useState(0)
    const [dataHistoria, setDataHistoria] = useState(null)


    useEffect(() => {
            if(editarHistoria != null){
                document.querySelector("#formHistoria .buttonApagar").style.display = 'block' 
                setNomeHistoria(editarHistoria[0][0])
                setHistoria(editarHistoria[0][1])
                setEscolha(editarHistoria[1])
                const data = new Date(editarHistoria[0][2])
                setDataHistoria(data.toLocaleDateString("pt-BR"))
            }else{
                document.querySelector("#formHistoria .buttonApagar").style.display = 'none' 
            }
        }, [editarHistoria])

    const handleSubmit = (e) => {
        e.preventDefault()
        aoEnviar(nomeHistoria, historia, escolha, )
        fecharFormulario()
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
        if(document.querySelector("#formHistoria .msgConfirmacao").classList.contains("hidden")){
            document.querySelector("#formHistoria .msgConfirmacao").classList.remove("hidden")
        }else{
            document.querySelector("#formHistoria .msgConfirmacao").classList.add("hidden")
        }
    }

    const handleCancelDelete = () => {
        mostrarConfirmacao()
    }

    const handleDeleteEtiqueta = (e) => {
        e.preventDefault()
        aoClicar()
        mostrarConfirmacao()
        fecharFormulario()
    }

    return(
        <div id="formHistoria">
            <form onSubmit={handleSubmit} autoComplete='off' id='formPrincipalHistoria'>
                <button onClick={fecharFormulario} id='fecharFormulario'>Voltar</button>
                <h2>Nova história</h2>
                <div>
                    <p><label htmlFor="tituloHistoria">Titulo: </label><input type="text" id="tituloHistoria" required placeholder="Nome da história" value={nomeHistoria} onChange={(e) => {setNomeHistoria(e.target.value)}} /></p>
                    <p>
                        <label htmlFor="escolhaEtiqueta">Etiqueta: </label>
                        <select id="escolhaEtiqueta" onChange={(e) => setEscolha(e.target.value)} value={escolha}>
                            {etiquetas.map((etiqueta, index) => (
                                <option value={index} key={index}>{etiqueta[0]}</option>
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
                {(dataHistoria != null) ? <div id="dataHistoria">Data de criação: {dataHistoria}</div> : null}
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