import "./App.css"
import React, { useState, useEffect } from 'react'

import EtiquetaForm from "./EtiquetaForm/EtiquetaForm"
import HistoriaForm from "./HistoriaForm/HistoriaForm"
import Grafico from "./grafico/Grafico.jsx"
import Configuration from "./Configuration/Configuration.jsx"
import botaoGrafico from './assets/botaoGrafico.png'
import botaoConfig from './assets/botaoConfig.png'
import Mensagem from './Mensagem/Mensagem.jsx'

function App() {

  const [etiquetas, setEtiquetas] = useState([])
  const [idEtiqueta, setIdEtiqueta] = useState(-1)

  const [carregou, setCarregou] = useState(false)

  const [historias, setHistorias] = useState([])
  const [historiaSelecionada, setHistoriaSelecionada] = useState(null);
  const [idHistoria, setIdHistoria] = useState(-1)
  const [idEscolhaOriginal, setIdEscolhaOriginal] = useState(-1)

  const [graficoVisivel, setGraficoVisivel] = useState(false)

  const [textoMensagem, setTextoMensagem] = useState(null)
  const [textoMensagemVisivel, setTextoMensagemVisivel] = useState(false)

  useEffect(() => {
    if (!carregou) {
      const etiquetasArmazenadas = JSON.parse(localStorage.getItem("etiquetas"));
      const historiasArmazenadas = JSON.parse(localStorage.getItem("historias"));
      if (etiquetasArmazenadas) {
        setEtiquetas(etiquetasArmazenadas);
        if (historiasArmazenadas) {
          setHistorias(historiasArmazenadas);
        }
      }
      setCarregou(true);
    }
  }, [carregou])


  useEffect(() => {
    if (carregou) {
      localStorage.setItem("etiquetas", JSON.stringify(etiquetas))
    }
  }, [etiquetas, carregou])

  useEffect(() => {
    if (carregou) {
      localStorage.setItem("historias", JSON.stringify(historias))
    }
  }, [historias, carregou])

  const handleShowEtiquetaForm = (id = -1) => {
    setIdEtiqueta(id)
    document.querySelector("#formEtiqueta").style.display = "flex"
    document.body.style.overflowY = "hidden"
    document.querySelector("#nomeEtiqueta").focus()
  }

  const handleShowHistoriaForm = (id = -1, idEtiqueta = -1) => {
    setHistoriaSelecionada(null)
    setIdHistoria(-1)
    if (id != -1) {
      setHistoriaSelecionada([historias[idEtiqueta][id], idEtiqueta])
      setIdHistoria(id)
      setIdEscolhaOriginal(idEtiqueta)
      
    }
    document.querySelector("#formHistoria").style.display = "flex"
    document.body.style.overflowY = "hidden"
    document.querySelector("#tituloHistoria").focus()
  }

  const handleEtiqueta = (valor) => {
    if (idEtiqueta != -1) {
      let temp = [...etiquetas]
      temp[idEtiqueta][0] = valor
      setEtiquetas([...temp])
    } else {
      let time = new Date()
      let anoAtual = time.getFullYear()
      let mesAtual = time.getMonth() + 1
      let diaAtual = time.getDate()
      let horaAtual = time.getHours()
      let minutoAtual = time.getMinutes()
      let segundoAtual = time.getSeconds()
      let tempEtiqueta = [valor, `${anoAtual}/${mesAtual}/${diaAtual} ${horaAtual}:${minutoAtual}:${segundoAtual}`]
      setEtiquetas([...etiquetas, tempEtiqueta])
      let tempHistorias = [...historias]
      tempHistorias.push([])
      setHistorias(tempHistorias)
    }

    setIdEtiqueta(-1)
    
    mostrarMensagem("Etiqueta salva!")

  }

  const handleHistoria = (titulo, historia, escolha) => {
    let tempArrayHistoria = null

    if (historiaSelecionada != null) {
      tempArrayHistoria = [...historias]
      if (escolha == idEscolhaOriginal && escolha != -1) {
        tempArrayHistoria[escolha][idHistoria][0] = titulo
        tempArrayHistoria[escolha][idHistoria][1] = historia
      } else if (escolha != -1 && escolha != idEscolhaOriginal) {
        tempArrayHistoria[escolha].push([titulo, historia])
        tempArrayHistoria[idEscolhaOriginal].splice(idHistoria, 1)
      }
    } else {
      tempArrayHistoria = [...historias]
      let time = new Date()
      let anoAtual = time.getFullYear()
      let mesAtual = time.getMonth() + 1
      let diaAtual = time.getDate()
      let horaAtual = time.getHours()
      let minutoAtual = time.getMinutes()
      let segundoAtual = time.getSeconds()
      tempArrayHistoria[escolha].push([titulo, historia, `${anoAtual}/${mesAtual}/${diaAtual} ${horaAtual}:${minutoAtual}:${segundoAtual}`])
    }
    setHistorias(tempArrayHistoria)
    setIdHistoria(-1)
    setIdEscolhaOriginal(-1)

    mostrarMensagem("História salva!")

  }

  const handleApagarEtiqueta = () => {
    let etiquetasAtualizadas = etiquetas.filter((_, index) => index !== idEtiqueta);
    let historiasAtualizadas = historias.filter((_, index) => index != idEtiqueta)
    setEtiquetas(etiquetasAtualizadas)
    setHistorias(historiasAtualizadas)
    setIdEtiqueta(-1)

    mostrarMensagem("Etiqueta apagada!")

  }

  const handleApagarHistoria = () => {
    let tempHistoria = [...historias]
    tempHistoria[idEscolhaOriginal] = tempHistoria[idEscolhaOriginal].filter((_, index) => index !== idHistoria)
    setHistorias(tempHistoria)
    setIdHistoria(-1)
    setIdEscolhaOriginal(-1)

    mostrarMensagem("História apagada!")

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

  const importedValues = (importEtiquetas, importHistorias) => {
    setEtiquetas(importEtiquetas)
    setHistorias(importHistorias)
    mostrarMensagem("Importação realizada!")
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

  function showConfigPopUp() {
    if (document.querySelector("#config").classList.contains("hidden")) {
      document.querySelector("#config").classList.remove("hidden")
    } else {
      document.querySelector("#config").classList.add("hidden")
      if(!document.querySelector("#inputFileContainer").classList.contains("hidden")){
          document.querySelector("#inputFileContainer").classList.add("hidden")
      }
    }
  }

  function mostrarMensagem(texto){

    setTextoMensagem(texto)
    setTextoMensagemVisivel(true)

  }

  const colherEstado = (estado) => {
    if(estado == true){
      setTextoMensagemVisivel(false)
      setTextoMensagem(null)
    }
  }

  return (
    <>
      <Mensagem texto={textoMensagem} visivel={textoMensagemVisivel} estado={colherEstado} />
      <h1>Um Pouco de Tudo</h1>
      <button id="configButton" onClick={showConfigPopUp}>
        <img src={botaoConfig} alt="" />
      </button>
      <button id="graficoButton" onClick={showGraficoPopUp}>
        <img src={botaoGrafico} alt="" />
      </button>
      <Configuration etiquetas={etiquetas} historias={historias} aoEnviar={importedValues}/>
      <Grafico etiquetas={etiquetas} historias={historias} visivel={graficoVisivel} />
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

          {(etiquetas.length > 0) ? etiquetas.map((etiqueta, keyEtiqueta) => {
            return (
              <div className="etiqueta" key={keyEtiqueta} onClick={() => handleShowEtiquetaForm(keyEtiqueta)}>
                <p>{etiqueta[0]}</p>
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
          {etiquetas.map((etiqueta, keyEtiqueta) => {
            return (

              <div className={(historias[keyEtiqueta]?.length == 0 || historias.length == 0) ? "historiaContainer naoHaContainer" : "historiaContainer"} key={keyEtiqueta} >
                <div className="historiaHeader iconeUncollapse" id={"historiaHeader" + keyEtiqueta} onClick={() => handleCollapse(keyEtiqueta)} >
                  <div className="icone"></div>
                  <h2>{etiqueta[0]}</h2>
                </div>
                <div className="historiasList collapsed">
                  {(historias[keyEtiqueta]?.length > 0) ? historias[keyEtiqueta]?.map((historia, keyHistoria) => {
                    return (
                      <div className="historia" key={keyHistoria} onClick={() => handleShowHistoriaForm(keyHistoria, keyEtiqueta)}>
                        <p>{historia[0]}</p>
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

      <EtiquetaForm aoEnviar={handleEtiqueta} editarEtiqueta={idEtiqueta !== -1 ? etiquetas[idEtiqueta] : null} aoClicar={handleApagarEtiqueta} />
      <HistoriaForm aoEnviar={handleHistoria} editarHistoria={historiaSelecionada} aoClicar={handleApagarHistoria} etiquetas={etiquetas} />

    </>
  )

}

export default App
