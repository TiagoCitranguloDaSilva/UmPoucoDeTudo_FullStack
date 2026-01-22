

async function doFetch(onRedirect, rota, metodo = "get", body = null, headersExtras, needToBeAuthenticated = true) {



    let token = localStorage.getItem("userToken")


    if (!token && needToBeAuthenticated) {
        onRedirect("login")
        return null;
    }

    let headers = {
        ...headersExtras
    }

    if(needToBeAuthenticated){
        headers["Authorization"] = token
    }

    if (body) {
        body = JSON.stringify(body)
        headers["Content-Type"] = "application/json"
    }


    const response = await fetch(rota, {
        "method": metodo,
        "body": body,
        headers: headers
    })

    if (response.status == 403) {
        localStorage.setItem("userToken", "")

        if(!needToBeAuthenticated) return null

        onRedirect("/login")
        return null
    }
    if (!response.ok) {
        return null;
    }

    let data;
    if (response.headers.get("Content-Type")?.includes("application/json")) {
        data = await response.json()
    }else{
        data = await response.text()
    }

    if(data == "") return true

    if (!data) return null

    return data;

}

export default doFetch;