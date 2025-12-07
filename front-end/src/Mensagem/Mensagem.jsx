
import "./Mensagem.css"
import { useEffect, useState } from "react";

function Mensagem({texto, visivel, estado}){

    const [mostrada, setMostrada] = useState(false)

    useEffect(() => {

        if(visivel == true && !mostrada){
            setMostrada(true)
            setTimeout(() => {
                estado(true)
            }, 5000)
        }else{
            setMostrada(false)
        }

    }, [visivel])


    return(
        <div className={visivel ? "mensagem" : "mensagem hidden"}>
            <p>{texto}</p>
        </div>
    )
}

export default Mensagem;