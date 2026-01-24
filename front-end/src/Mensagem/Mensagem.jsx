
import { useEffect, useState } from "react";
import "./Mensagem.css";

function Mensagem({ texto, visivel, estado, falha = false }) {

    const [mostrada, setMostrada] = useState(false)

    useEffect(() => {

        if (visivel == true && !mostrada) {
            setMostrada(true)
            setTimeout(() => {
                estado(true)
            }, 5000)
        } else {
            setMostrada(false)
        }

    }, [visivel])


    return (
        <div className={`mensagem ${(!visivel && "hidden")} ${(falha) ? "falha" : "sucesso"}`}>
            <p>{texto}</p>
        </div>
    )
}

export default Mensagem;