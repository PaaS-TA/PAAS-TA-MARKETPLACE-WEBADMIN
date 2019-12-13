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
        headers: {
        	"X-ERROR-RESPONSE-FORMAT": "JSON"
        },
        beforeSend: function(xhr){
        },
        success: function(data) {
        	if (!commonUtils.isEmpty(callback)) {
                callback(data);        		
        	}
        },
        error: function(jqXHR, exception) {
        	try {
        		// 열려있는 Modal Close
        		$('.modal').each(function () {
                    $(this).modal('hide');
                });
        		
        		// 실행되고 있는 Loading처리 Stop
        		if (loading.isLoading()) {
        			loading.stop();
        		}
        		
        		commonAlert.show("서버와의 통신 중 연결이 끊기거나 오류가 발생하였습니다.<br>새로고침을 하시거나 다시 한번 시도해주세요.");
        	} catch (e) {
        		console.log(e);
        	}
            console.log("jqXHR.status::::"+jqXHR.status+" exception:::"+exception);
        },
        complete : function(data) {
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
		if (this.isBlank(num)) {
			return num;
		}
		var regexp = /\B(?=(\d{3})+(?!\d))/g;
		return num.toString().replace(regexp, ',');
	},
	dateValueDigit: function(value) {
    	var result = value;
        if (value < 10) {
            result = '0' + value;
        }
		return result;
    }, 
    isEmpty: function(object) {
    	if (object == undefined || object == null) {
    		return true;
    	}
    	return false;
    }, 
    isBlank: function(value) {
    	if (value == undefined || value == null || value == "") {
    		return true;
    	}
    	return false;
    },
    replaceEnter: function(value) {
    	if (this.isEmpty(value)) {
    		return value;
    	}
    	return value.replace(/(\r?\n|\r)/gm,'');
    },
    contains: function(contents, findString) {
    	if (this.isBlank(contents) || this.isBlank(findString)) {
    		return false;
    	}
    	return contents.includes(findString);
    },
    isEmptyArray: function(object) {
    	if (object == undefined || object == null || object.length <= 0) {
    		return true;
    	}
    	return false;
    }, 
    arrayContains: function(arrayObj, findValue) {
    	var result = false;
    	
    	if (this.isEmptyArray(arrayObj) || this.isBlank(findValue)) {
    		return result;
    	}
    	
    	arrayObj.forEach(function(element, index, array) {
    		if (findValue == element) {
    			result = true;
    			return;
    		}
    	});
    	return result;
    },
    sleep: function(delayTimeMillis) {
 	   var start = new Date().getTime();
 	   while (new Date().getTime() < (start + delayTimeMillis));
	},
	defaultString: function(value, defaultValue) {
		if (this.isBlank(value) || value == "null") {
			return defaultValue;
		}
		return value;
	},
	onlyNumber: function(value) {
		if (this.isBlank(value)) {
			return value;
		}
		var regexp = /[^0-9]/g;
		return value.toString().replace(regexp, '');
	}
}

/*
 * Loding View
 * http://carlosbonetti.github.io/jquery-loading/
 * */
var loading = {
	timeoutList: [],
	start: function(msg) {
		var msgValue = "LOADING...";
		
		if (!commonUtils.isBlank(msg)) {
			msgValue = msg;
		}
		
		$('body').loading({
			stoppable: false,
			theme: 'dark',
			message: msgValue
     	});
	},
	stop: function() {
		if (!commonUtils.isEmpty(this.timeoutList) && this.timeoutList.length > 0) {
			var timeoutObj = {};
			for (var idx=0; idx<this.timeoutList.length ;idx++) {
				timeoutObj = this.timeoutList[idx];
				clearTimeout(timeoutObj);
			}
			this.timeoutList = [];
		}
		$('body').loading("stop");
	},
	interval: function(value, msg) {
		var intervalTime = 1000 * value;
		this.start(msg);
		
		this.timeoutList[this.timeoutList.length] = setTimeout(function() {
			$('body').loading('toggle');
		}, intervalTime);
	},
	isLoading: function() {
		return $('body').is(':loading');
	}
}

//공통 알림 Modal
var commonAlert = {
	callBackArguments: null,
	show: function(message, focusTargetId) {
		if (!commonUtils.isBlank(focusTargetId)) {
       		this.setCallBackFunc(function(){
				setTimeout(function(){
					$('html, body').animate({scrollTop : ($("#"+ focusTargetId).offset().top - 100)}, 200);
				}, 300);
			});
		}
		$("#commonAlertModal-Message").html(message);
		$("#commonAlertModal").modal("show");
	},
	hide: function() {
		$("#commonAlertModal-Message").html("");
		$("#commonAlertModal").modal("hide");
	},
	setCallBackFunc: function() {
		this.callBackArguments = arguments;
	},
	excCallBackFunc: function() {
		var execFunction = "";
		if (commonUtils.isEmpty(this.callBackArguments)) {
			return;
		}
		
		if (typeof this.callBackArguments[0] !== "function") {
			this.callBackArguments = null;
			return;
		}

		try {
			if (commonUtils.isEmpty(this.callBackArguments) || this.callBackArguments.length > 1) {
				execFunction = "this.callBackArguments[0](";
				var argCount = this.callBackArguments.length;
				
				for (var idx=1; idx<argCount ;idx++) {
					if (idx == 1) {
						execFunction += "this.callBackArguments["+ idx +"]";
					} else {
						execFunction += ",this.callBackArguments["+ idx +"]";
					}
				}
				
				execFunction += ")";
			} else {
				execFunction = "this.callBackArguments[0]()";
			}
		
			eval(execFunction);
		} catch(error) {
			console.log(error.message);
		}

		this.callBackArguments = null;
	}
}