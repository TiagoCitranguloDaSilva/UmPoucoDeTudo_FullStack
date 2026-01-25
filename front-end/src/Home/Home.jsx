
import { useEffect, useRef, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import botaoGrafico from '../assets/botaoGrafico.png'
import EtiquetaForm from "../EtiquetaForm/EtiquetaForm"
import Grafico from "../Grafico/Grafico.jsx"
import HistoriaForm from "../HistoriaForm/HistoriaForm"
import Mensagem from '../Mensagem/Mensagem.jsx'

import doFetch from '../util/fetchModel.js'

function Home() {

  const [etiquetas, setEtiquetas] = useState([])
  const [idEtiqueta, setIdEtiqueta] = useState(-1)

  const [historias, setHistorias] = useState([])
  const [idHistoria, setIdHistoria] = useState(-1)

  const [formEtiquetaVisivel, setFormEtiquetaVisivel] = useState(false)

  const [formHistoriaVisivel, setFormHistoriaVisivel] = useState(false)

  const [graficoVisivel, setGraficoVisivel] = useState(false)

  const [textoMensagem, setTextoMensagem] = useState(null)
  const [textoMensagemVisivel, setTextoMensagemVisivel] = useState(false)
  const [mensagemFailed, setMensagemFailed] = useState(false)

  const jaRodou = useRef(false)

  const navigate = useNavigate()

  useEffect(() => {

    if (jaRodou.current) return;
    jaRodou.current = true

    let mensagem = sessionStorage.getItem("mensagem")

    if (mensagem) {
      sessionStorage.setItem("mensagem", "")
      mostrarMensagem(JSON.parse(mensagem))
    }

    updateAll()
  }, [])

  const handleShowEtiquetaForm = (id = -1) => {
    setIdEtiqueta(id)
    setFormEtiquetaVisivel(true)
  }

  const handleShowHistoriaForm = (id = -1) => {
    setIdHistoria(-1)
    if (id != -1) {
      setIdHistoria(id)
    }
    setFormHistoriaVisivel(true)
  }

  const handleEtiqueta = (msg) => {

    updateAll()

    mostrarMensagem(msg)

  }

  const handleHistoria = (msg) => {

    setFormHistoriaVisivel(false)

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

  function mostrarMensagem(texto, failed = false) {

    setTextoMensagem(texto)
    setTextoMensagemVisivel(true)
    setMensagemFailed(failed)

  }

  const colherEstado = (estado) => {
    if (estado == true) {
      setTextoMensagemVisivel(false)
      setTextoMensagem(null)
    }
  }

  const updateAll = async () => {

    let responseEtiquetas = await doFetch("http://localhost:8080/tags/getAll")

    if (responseEtiquetas.httpStatusCode == 401) {
      sessionStorage.setItem("mensagem", JSON.stringify([responseEtiquetas.data, responseEtiquetas.failed]))
      navigate("/UmPoucoDeTudo/login")
      return
    }

    if (responseEtiquetas.httpStatusCode == 403) {
      navigate("/UmPoucoDeTudo/login")
      return
    }

    if (responseEtiquetas.httpStatusCode == 200) {
      setEtiquetas(JSON.parse(responseEtiquetas.data))
    }

  }

  return (
    <>

      <Mensagem texto={textoMensagem} visivel={textoMensagemVisivel} estado={colherEstado} failed={mensagemFailed} />
      <h1>Um Pouco de Tudo</h1>
      <button id="graficoButton" onClick={showGraficoPopUp}>
        <img src={botaoGrafico} alt="" />
      </button>
      <Grafico visivel={graficoVisivel} etiquetas={etiquetas} historias={historias} />
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
                  {(etiqueta.stories?.length > 0) ? etiqueta.stories?.map((historia) => {
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

      <EtiquetaForm aoEnviar={handleEtiqueta} idEtiqueta={idEtiqueta} visivel={formEtiquetaVisivel} fecharForm={setFormEtiquetaVisivel} />
      <HistoriaForm aoEnviar={handleHistoria} idHistoria={idHistoria} visivel={formHistoriaVisivel} fecharForm={setFormHistoriaVisivel} />

    </>
  )
}

export default Home;