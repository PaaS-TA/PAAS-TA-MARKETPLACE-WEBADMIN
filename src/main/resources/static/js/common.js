/**
 * API call function
 * @param reqUrl      : Request URL
 * @param reqMethod   : Request Method (GET POST PUT DELETE ..)
 * @param param       : Request Parameters
 * @param preFunc     : Pre function
 * @param callback    : after function
 * @description
 *      async: false 동기 처리
 * @author hrjin
 * @since 2019.05.07
 */
var procCallAjax = function(reqUrl, reqMethod, param, preFunc, callback) {
    var reqData = "";
    if (param != null) {
        reqData = param;
    }
    $.ajax({
        url: reqUrl,
        method: reqMethod,
        data: reqData,
        dataType: 'json',
        async: false,
        contentType: "application/json",
        beforeSend: function(xhr){
            // preFunc
            if(_csrf_header && _csrf_token) {
                xhr.setRequestHeader(_csrf_header, _csrf_token);
            }
        },
        success: function(data) {
            callback(data);
        },
        error: function(jqXHR, exception) {
            console.log("jqXHR.status::::"+jqXHR.status+" exception:::"+exception);
        },
        complete : function(data) {
            // SKIP
            console.log("COMPLETE :: data :: ", data);
        }
    });
};


// MOVE PAGE
var procMovePage = function (pageUrl) {
    if (pageUrl === null || pageUrl.length < 1) {
        return false;
    }

    if ((!!pageUrl && typeof pageUrl === 'number') && -1 === pageUrl) {
        history.back();
    } else {
        // pageUrl = ("/" === pageUrl) ? "" : pageUrl;
        // location.href = procGetDashboardUrl() + pageUrl;
        location.href = pageUrl;
    }

};
