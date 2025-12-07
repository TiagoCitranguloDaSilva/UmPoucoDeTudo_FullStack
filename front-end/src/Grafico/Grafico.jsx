
import React, { useState, useEffect, useRef } from 'react'
import Chart from 'chart.js/auto';
import "./Grafico.css"

function Grafico({ etiquetas, historias, visivel }) {

    const canvasRef = useRef(null)
    const [chart, setChart] = useState()
    const [data, setData] = useState(
        {
            labels: [],
            datasets: [],
        }
    )

    useEffect(() => {
        if (visivel == false || !canvasRef.current) {
            return
        }

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
                if (historias[c].length >= 1) {
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

    function atualizarData(novasEtiquetas, novasHistorias) {
        setData({
            labels: novasEtiquetas.map((etiqueta, key) => etiqueta[0]),
            datasets: [
                {
                    label: "Histórias",
                    data: novasEtiquetas.map((etiqueta, key) => historias[key].length),
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