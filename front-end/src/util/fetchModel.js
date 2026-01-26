

async function doFetch(rota, metodo = "get", body = null, headersExtras, needToBeAuthenticated = true) {

    let token = localStorage.getItem("userToken")

    if (needToBeAuthenticated && !token) {
        return {
            data: "",
            httpStatusCode: 403,
            failed: true
        };
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
            method: metodo,
            body: body,
            headers: headers
        })
    } catch (exception) {
        return;
    }

    return {
        data: await response.text(),
        httpStatusCode: response.status,
        failed: !response.ok
    };

}

export default doFetch;