import "./Configuration.css"
import { useState, useEffect } from 'react'

function Configuration({ etiquetas, historias, aoEnviar }) {

    const [arquivoImport, setArquivoImport] = useState(null)
    const [importContent, setImportContent] = useState(null)

    useEffect(() => {
        if(importContent != null){
            aoEnviar(importContent.etiquetas, importContent.historias)
        }
    }, [importContent])

    const handleExport = () => {
        if(!document.querySelector("#inputFileContainer").classList.contains("hidden")){
            document.querySelector("#inputFileContainer").classList.add("hidden")
        }
        let conteudoEtiquetas = JSON.stringify(etiquetas)
        let conteudoHistorias = JSON.stringify(historias)
        let conteudo = [`{\"etiquetas\": ${conteudoEtiquetas},\n\"historias\": ${conteudoHistorias}}`]

        let blob = new Blob(conteudo, { type: "json/application" })
        const url = URL.createObjectURL(blob);

        const a = document.createElement("a");
        a.href = url;
        a.download = "backupUmPoucoDeTudo.json"; 
        a.click();

        URL.revokeObjectURL(url);
    }

    const showInputFileContainer = () => {
        if(document.querySelector("#inputFileContainer").classList.contains("hidden")){
            document.querySelector("#inputFileContainer").classList.remove("hidden")
        }else{
            document.querySelector("#inputFileContainer").classList.add("hidden")
        }
    }

    const submitFormInputFile = (e) => {
        e.preventDefault()
        let fileReader = new FileReader()

        fileReader.onload = () => {
            setImportContent(JSON.parse(fileReader.result))
        }

        fileReader.readAsText(arquivoImport)
        if(!document.querySelector("#inputFileContainer").classList.contains("hidden")){
            document.querySelector("#inputFileContainer").classList.add("hidden")
        }
        document.querySelector("#arquivo").value = ""
    }

    const handleChangeArquivo = (e) => {
        setArquivoImport(e.target.files[0])
    }

    return (
        <div id="config" className="hidden">
            <h1>Configurações</h1>
            <div id="configContainer">
                <div id="importExportContainer">
                    <h2>Exportar e importar dados</h2>
                    <div id="exportImportButtons">
                        <button onClick={handleExport}>Exportar</button>
                        <button onClick={showInputFileContainer}>Importar</button>
                    </div>
                    <div id="inputFileContainer" className="hidden">
                        <form id="formInputFile" onSubmit={(e) => submitFormInputFile(e)}>
                            <h3>Selecione o arquivo:</h3>
                            <p>Cuidado! Dados importados substituirão todos os dados já existentes</p>
                            <input type="file" id="arquivo" required onChange={(e) => handleChangeArquivo(e)} />
                            <input type="submit" value="Importar" />
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )

}

export default Configuration;