import React, { useState, useEffect } from 'react'
import "./EtiquetaForm.css"

function NovaEtiquetaForm({aoEnviar, editarEtiqueta, aoClicar}){

    const [nomeEtiqueta, setNomeEtiqueta] = useState("")
    const [dataEtiqueta, setDataEtiqueta] = useState(null)
    const [editBool, setEditBool] = useState(false)

    useEffect(() => {
        if(editarEtiqueta != null){
            setEditBool(true)
            document.querySelector(".buttonApagar").style.display = 'block' 
            setNomeEtiqueta(editarEtiqueta[0])
            const data = new Date(editarEtiqueta[1])
            const dataFormatada = data.toLocaleDateString('pt-BR')
            setDataEtiqueta(dataFormatada)
        }else{
            setEditBool(false)
            document.querySelector(".buttonApagar").style.display = 'none' 
        }
    }, [editarEtiqueta])

    const handleSubmit = (e) => {
        e.preventDefault()
        aoEnviar(nomeEtiqueta)
        fecharFormulario()
    }

    const fecharFormulario = () => {
        document.querySelector("#formEtiqueta").style.display = "none"
        document.body.style.overflowY = "auto"
        setNomeEtiqueta("")
        setDataEtiqueta(null)
    }

    const mostrarConfirmacao = () => {
        if(document.querySelector("#formEtiqueta .msgConfirmacao").classList.contains("hidden")){
            document.querySelector("#formEtiqueta .msgConfirmacao").classList.remove("hidden")
        }else{
            document.querySelector("#formEtiqueta .msgConfirmacao").classList.add("hidden")
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
        <div id="formEtiqueta">
            <form onSubmit={handleSubmit} autoComplete='off' id='formPrincipalEtiqueta'>
                <button onClick={fecharFormulario} id='fecharFormulario' type='button'>Fechar</button>
                <h2>Nova etiqueta</h2>
                <p><label htmlFor="nomeEtiqueta">Nome: </label><input type="text" id="nomeEtiqueta" required placeholder="Nome da etiqueta" value={nomeEtiqueta} maxLength={50} onChange={(e) => {setNomeEtiqueta(e.target.value)}} /></p>

                <div className='botoes'>
                    <input type="submit" value="Salvar" />
                    <button className='buttonApagar' type='button' onClick={
                        (e) => {
                            e.preventDefault()
                            mostrarConfirmacao()
                        }
                        }>Apagar</button>

                </div>
                {(dataEtiqueta != null) ? <div id="dataEtiqueta">Criado em: {dataEtiqueta}</div> : null}
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