
import React, { useState, useEffect, useRef } from 'react'
import Chart from 'chart.js/auto';
import "./Grafico.css"
import { useNavigate } from 'react-router-dom';
import doFetch from '../util/fetchModel';

function Grafico({ visivel }) {

    const canvasRef = useRef(null)
    const [chart, setChart] = useState()
    const [data, setData] = useState(
        {
            labels: [],
            datasets: [],
        }
    )

    const navigate = useNavigate()

    const [etiquetas, setEtiquetas] = useState([])
    const [historias, setHistorias] = useState([])

    useEffect(() => {
        if (visivel == false || !canvasRef.current) {
            return
        }

        updateAll()

        const ctx = canvasRef.current.getContext("2d")

        if (!ctx) {
            return
        }

        let myChart = new Chart(ctx, {
            type: "pie",
            data: data,
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'right',
                    }
                }
            }
        });

        setChart(myChart)

        return () => {
            myChart.destroy()
        }
    }, [visivel])

    useEffect(() => {
        updateAll()
    }, [])

    useEffect(() => {

        if (etiquetas.length == 0) {
            document.querySelector("#msgNaoHaDados").style.display = "flex"
            setData(
                {
                    labels: [],
                    datasets: [],
                }
            )
            return;
        } else {
            let achou = false
            for (let c = 0; c < etiquetas.length; c++) {
                if (historias[etiquetas[c].id]?.length >= 1) {
                    achou = true
                }
            }
            if (!achou) {
                document.querySelector("#msgNaoHaDados").style.display = "flex"
                setData(
                    {
                        labels: [],
                        datasets: [],
                    }
                )
                return;
            }
            document.querySelector("#msgNaoHaDados").style.display = "none"
        }

        atualizarData(etiquetas, historias)

    }, [etiquetas, historias])



    useEffect(() => {
        if (chart && data && visivel) {
            chart.data = data
            chart.update()
        }
    }, [data])

    function atualizarData(novasEtiquetas) {

        setData({
            labels: novasEtiquetas.map((etiqueta) => etiqueta.name),
            datasets: [
                {
                    label: "Histórias",
                    data: novasEtiquetas.map((etiqueta) => historias[etiqueta.id]?.length),
                    backgroundColor: aleatorizarCores(novasEtiquetas.length)
                }
            ]
        });
    }

    function aleatorizarCores(qtde) {

        let cores = []
        let r, g, b, rgb

        for (let c = 0; c < qtde; c++) {

            do {
                r = Math.ceil(Math.random() * 200)
                g = Math.ceil(Math.random() * 200)
                b = Math.ceil(Math.random() * 200)
                rgb = `rgb(${r}, ${g}, ${b})`
            } while (cores.includes(rgb));

            cores.push(rgb)
        }
        return cores

    }

    async function updateAll() {

        const responseEtiquetas = await doFetch(navigate, "http://localhost:8080/tags/getAll")
        if (responseEtiquetas) setEtiquetas(responseEtiquetas)

        const responseHistorias = await doFetch(navigate, "http://localhost:8080/stories/getAll")
        if (responseHistorias) {
            const agrupado = responseHistorias.reduce((tag, historia) => {
                const tagId = historia.tag.id;
                if (!tag[tagId]) {
                    tag[tagId] = [];
                }
                tag[tagId].push(historia);
                return tag;
            }, {});
            setHistorias(agrupado)
        }

    }



    return (
        <div id="grafico" className='hidden'>
            <h1>Relação entre histórias e etiquetas</h1>
            <div id="msgNaoHaDados">
                <h2>Não há dados o suficiente para mostrar o gráfico</h2>
            </div>
            <div id='containerGrafico'>
                <canvas id="canvas" ref={canvasRef}></canvas>
            </div>
        </div>
    )

}

export default Grafico;