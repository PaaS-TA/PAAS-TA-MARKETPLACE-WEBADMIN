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
var procCallAjax = function(reqUrl, reqMethod, param, preFunc, callback, useAsync) {
    var reqData = "";
    if (param != null) {
        reqData = param;
    }

    if(useAsync == null) {
        useAsync = false;
    }

    $.ajax({
        url: reqUrl,
        method: reqMethod,
        data: reqData,
        dataType: 'json',
        async: useAsync,
        contentType: "application/json",
        beforeSend: function(xhr){
            // preFunc
            // if(_csrf_header && _csrf_token) {
            //     xhr.setRequestHeader(_csrf_header, _csrf_token);
            // }
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

// Format Date
function getFormatDate(date) {
    var year = date.getFullYear(); //yyyy
    var month = (1 + date.getMonth()); //M
    month = month >= 10 ? month : '0' + month; //month 두자리로 저장
    var day = date.getDate(); //d
    day = day >= 10 ? day : '0' + day; //day 두자리로 저장
    return year + '-' + month + '-' + day;
}

// Prevent button multiple clicks
var doubleSubmitFlag = false;
function doubleSubmitCheck(){
    if(doubleSubmitFlag){
        return doubleSubmitFlag;
    }else{
        doubleSubmitFlag = true;
        return false;
    }
}

var commonUtils = {
	addComma: function(num) {
		return num;
		// var regexp = /\B(?=(\d{3})+(?!\d))/g;
		// return num.toString().replace(regexp, ',');
	},
	dateValueDigit: function(value) {
    	var result = value;
        if (value < 10) {
            result = '0' + value;
        }
		return result;
    }, 
    isEmpty(object) {
    	if (object == undefined || object == null) {
    		return true;
    	}
    	return false;
    }, 
    isBlank(value) {
    	if (value == undefined || value == null || value == "") {
    		return true;
    	}
    	return false;
    },
    replaceEnter(value) {
    	if (this.isEmpty(value)) {
    		return value;
    	}
    	return value.replace(/(\r?\n|\r)/gm,'');
    }
}

/*
 * Loding View
 * http://carlosbonetti.github.io/jquery-loading/
 * */
var loading = {
	start: function() {
		$('body').loading({
			stoppable: false
			,theme: 'dark'
			//,message: 'Working...'
     	});
	},
	stop: function() {
		$('body').loading("stop");
	}
}
