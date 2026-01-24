

async function doFetch(onRedirect, rota, metodo = "get", body = null, headersExtras, needToBeAuthenticated = true) {



    let token = localStorage.getItem("userToken")


    if (!token && needToBeAuthenticated) {
        onRedirect("login")
        return null;
    }

    let headers = {
        ...headersExtras
    }

    if (needToBeAuthenticated) {
        headers["Authorization"] = token
    }

    if (body) {
        body = JSON.stringify(body)
        headers["Content-Type"] = "application/json"
    }


    let response

    try {
        response = await fetch(rota, {
            "method": metodo,
            "body": body,
            headers: headers
        })
    } catch (exception) {
        onRedirect("login")
        return;
    }

    if (response.status == 403) {
        localStorage.setItem("userToken", "")

        if (!needToBeAuthenticated) return null

        onRedirect("/UmPoucoDeTudo/login")
        return null
    }
    if (!response.ok) {
        return null;
    }

    let data;
    if (response.headers.get("Content-Type")?.includes("application/json")) {
        data = await response.json()
    } else {
        data = await response.text()
    }

    if (data == "") return true

    if (!data) return null

    return data;

}

export default doFetch;