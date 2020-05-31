$.sendJSON = function(type, url, json, onSuccess, onFail) {
    $.ajax({
        beforeSend: function (xhrObj) {
            xhrObj.setRequestHeader("Content-Type", "application/json;text/json;");
            xhrObj.setRequestHeader("Accept", "application/json;text/json;");
        },
        type: type,
        url: url,
        data: JSON.stringify(json),
        dataType: "json",
        success: function (jsonResponse) {
            if (onSuccess !== undefined)
                onSuccess(jsonResponse)
        },
        error: function(xhr, status, err) {
            if (onFail !== undefined)
                onFail(xhr, status, err)
        }
    });
}

$.postJSON = function(url, json, success, fail) {
    $.sendJSON("POST", url, json, success, fail)
};
