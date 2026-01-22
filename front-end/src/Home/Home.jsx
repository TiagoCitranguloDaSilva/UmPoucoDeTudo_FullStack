
import React, { useState, useEffect } from 'react'
import EtiquetaForm from "../EtiquetaForm/EtiquetaForm"
import HistoriaForm from "../HistoriaForm/HistoriaForm"
import Grafico from "../grafico/Grafico.jsx"
import botaoGrafico from '../assets/botaoGrafico.png'
import Mensagem from '../Mensagem/Mensagem.jsx'

function Home() {

    
  const [etiquetas, setEtiquetas] = useState([])
  const [idEtiqueta, setIdEtiqueta] = useState(-1)

  const [historias, setHistorias] = useState([])
  const [idHistoria, setIdHistoria] = useState(-1)

  const [graficoVisivel, setGraficoVisivel] = useState(false)

  const [textoMensagem, setTextoMensagem] = useState(null)
  const [textoMensagemVisivel, setTextoMensagemVisivel] = useState(false)

  useEffect(() => {
    updateAll()
  }, [])

  const handleShowEtiquetaForm = (id = -1) => {
    setIdEtiqueta(id)
    document.querySelector("#formEtiqueta").style.display = "flex"
    document.body.style.overflowY = "hidden"
    document.querySelector("#nomeEtiqueta").focus()
  }

  const handleShowHistoriaForm = (id = -1) => {
    setIdHistoria(-1)
    if (id != -1) {
      setIdHistoria(id)
    }
    document.querySelector("#formHistoria").style.display = "flex"
    document.body.style.overflowY = "hidden"
    document.querySelector("#tituloHistoria").focus()
  }

  const handleEtiqueta = (msg) => {

    updateAll()

    mostrarMensagem(msg)

  }

  const handleHistoria = (msg) => {

    setIdHistoria(-1)

    mostrarMensagem(msg)

    updateAll()

  }

  const handleCollapse = (id) => {

    let historiaList = document.querySelector(`#historiaHeader${id}`).parentNode.children[1]
    let historiaHeader = document.querySelector(`#historiaHeader${id}`)
    if (historiaList.classList.contains('collapsed')) {
      historiaList.classList.remove('collapsed')
      historiaHeader.classList.remove("iconeUncollapse")
      historiaHeader.classList.add("iconeCollapse")
    } else {
      historiaList.classList.add('collapsed')
      historiaHeader.classList.remove("iconeCollapse")
      historiaHeader.classList.add("iconeUncollapse")
    }
  }

  const collapseEtiqueta = () => {

    let tempEtiquetaList = document.querySelector(`#etiquetasHeader`).parentNode.children[2]
    let historiaHeader = document.querySelector(`#etiquetasHeader`)
    if (tempEtiquetaList.classList.contains('collapsed')) {
      tempEtiquetaList.classList.remove('collapsed')
      historiaHeader.classList.remove("iconeUncollapse")
      historiaHeader.classList.add("iconeCollapse")
    } else {
      tempEtiquetaList.classList.add('collapsed')
      historiaHeader.classList.remove("iconeCollapse")
      historiaHeader.classList.add("iconeUncollapse")
    }
  }

  function showGraficoPopUp() {
    if (document.querySelector("#grafico").classList.contains("hidden")) {
      document.querySelector("#grafico").classList.remove("hidden")
      setGraficoVisivel(true)
    } else {
      document.querySelector("#grafico").classList.add("hidden")
      setGraficoVisivel(false)
    }
  }

  function mostrarMensagem(texto) {

    setTextoMensagem(texto)
    setTextoMensagemVisivel(true)

  }

  const colherEstado = (estado) => {
    if (estado == true) {
      setTextoMensagemVisivel(false)
      setTextoMensagem(null)
    }
  }

  const updateAll = () => {

    fetch("http://localhost:8080/tags/getAll")
      .then(response => {
        if (!response.ok) {
          console.log("Erro ao pegar as etiquetas")
        }
        return response.json()
      })
      .then(data => {
        setEtiquetas(data)
      })

    fetch("http://localhost:8080/stories/getAll")
      .then(response => {
        if (!response.ok) {
          console.log("Erro ao pegar as histórias")
        }
        return response.json()
      })
      .then(data => {
        const agrupado = data.reduce((tag, historia) => {
          const tagId = historia.tag.id;
          if (!tag[tagId]) {
            tag[tagId] = [];
          }
          tag[tagId].push(historia);
          return tag;
        }, {});
        setHistorias(agrupado)
      })

  }

    return (
        <>

            <Mensagem texto={textoMensagem} visivel={textoMensagemVisivel} estado={colherEstado} />
            <h1>Um Pouco de Tudo</h1>
            <button id="graficoButton" onClick={showGraficoPopUp}>
                <img src={botaoGrafico} alt="" />
            </button>
            <Grafico visivel={graficoVisivel} />
            <div id="etiquetas">
                <div className="headerContainer">
                    <h2>Etiquetas</h2>
                    <button onClick={() => handleShowEtiquetaForm()}>Nova etiqueta</button>
                </div>
                <div id="etiquetasHeader" className="iconeUncollapse" onClick={collapseEtiqueta}>
                    <div className="icone"></div>
                    <h2>Etiquetas</h2>
                </div>
                <div id="etiquetasContainer" className="collapsed">

                    {(etiquetas.length > 0) ? etiquetas.map((etiqueta) => {
                        return (
                            <div className="etiqueta" key={etiqueta.id} onClick={() => handleShowEtiquetaForm(etiqueta.id)}>
                                <p>{etiqueta.name}</p>
                            </div>)
                    }) : (<p className="msgNaoHa">Não há etiquetas</p>)}

                </div>
            </div>
            <div id="historias">
                <div className="headerContainer">
                    <h2>Histórias</h2>
                    <button onClick={() => handleShowHistoriaForm(-1)} disabled={etiquetas.length <= 0}>Nova história</button>
                </div>
                <div className="historias">
                    {etiquetas.map((etiqueta) => {
                        return (

                            <div className={(etiqueta.stories?.length == 0) ? "historiaContainer naoHaContainer" : "historiaContainer"} key={etiqueta.id} >
                                <div className="historiaHeader iconeUncollapse" id={"historiaHeader" + etiqueta.id} onClick={() => handleCollapse(etiqueta.id)} >
                                    <div className="icone"></div>
                                    <h2>{etiqueta.name}</h2>
                                </div>
                                <div className="historiasList collapsed">
                                    {(historias[etiqueta.id]?.length > 0) ? historias[etiqueta.id]?.map((historia) => {
                                        return (
                                            <div className="historia" key={historia.id} onClick={() => handleShowHistoriaForm(historia.id)}>
                                                <p>{historia.title}</p>
                                            </div>

                                        )
                                    }) : (
                                        <p className="msgNaoHa">Não há histórias</p>
                                    )}
                                </div>
                            </div>
                        )
                    })}


                </div>
            </div>

            <EtiquetaForm aoEnviar={handleEtiqueta} idEtiqueta={idEtiqueta} />
            <HistoriaForm aoEnviar={handleHistoria} idHistoria={idHistoria} />

        </>
    )
}

export default Home;