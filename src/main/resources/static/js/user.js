/*******************************************************************************
 * 파일명 : user.js
 * 제작자 : ohy
 * 일  자 : 2018-01-04
 * 내  용 : interaction script
 ******************************************************************************/
// Left Menu Script <신규좌측메뉴 스크립트>
// 현재 페이지의 메뉴에 class on
function nowHrefFunction (depth){
	var nowHost = $(location).attr('host');
	var nowPage = $(location).attr('href');
	var nowHref = nowPage.split(nowHost);
	
	$("#leftMenu").find('a').each(function(){
		var thisHref = $(this).attr("href");
		if(nowHref[1] == (thisHref)){
			$(this).closest('li.'+depth).find('a.'+depth).addClass("on");
		}
	});
}
//1depth click event
function depth1Click (evt) {

	var target = $(evt.currentTarget);
	
	if (target.hasClass("open")) {
		$("#leftMenu").find('a.dept1').removeClass("open on");
		
		nowHrefFunction("dept1");
		
	} else {
		$("#leftMenu").find('ul.dept2').hide(200);
		$("#leftMenu").find('a.dept1').removeClass("open on");
		
		nowHrefFunction("dept1");
		
		target.addClass("open on");
		$(target).closest("li.dept1").find("ul.dept2").toggle(200);
	}
};

//2depth hover event
function depth2Hover (evt,depth) {
	var target = $(evt.currentTarget);
	
	if(target.hasClass("open")){
		$("#leftMenu").find('ul.dept3').hide();
		$(target).parent().find("ul.dept3").show();
	}else{
		$("#leftMenu").find('a.dept2').removeClass("open on");
		$("#leftMenu").find('ul.dept3').hide();
		$(target).parent().find("ul.dept3").show();
		
		nowHrefFunction("dept2");

		if(depth != 'on'){
			target.addClass("open on");		
		}
	}
};

function depth2LiHover(evt,depth){
	var target = $(evt.currentTarget).find("a.dept2");
	
	if(target.hasClass("open")){
		$("#leftMenu").find('ul.dept3').hide();
		$(target).parent().find("ul.dept3").show();
	}else{
		$("#leftMenu").find('a.dept2').removeClass("open on");
		$("#leftMenu").find('ul.dept3').hide();
		$(target).parent().find("ul.dept3").show();
		
		nowHrefFunction("dept2");

		if(depth != 'on'){
			target.addClass("open on");		
		}
	}
}
//3depth click event
function depth3Click (evt) {
	var target = $(evt.currentTarget);
	$("#leftMenu").find('a').removeClass("on");
	$("#leftMenu").find('ul.dept3').hide();
	
	$(target).closest("li.dept2").find("a.dept2").addClass("on");
	$(target).closest("li.dept1").find("a.dept1").addClass("on");
	
	target.toggleClass("on");
};

function depth3Leave (){
	$("#leftMenu").find('a.dept2').removeClass("open on");
	$("#leftMenu").find('ul.dept3').hide();
	
	nowHrefFunction("dept2");
}



// Mobile Menu Script <신규좌측메뉴 스크립트>
// 현재 페이지의 메뉴에 class on
function nowHrefFunction_m (depth){
	var nowHost = $(location).attr('host');
	var nowPage = $(location).attr('href');
	var nowHref = nowPage.split(nowHost);
	
	$("#mobileMenu").find('a').each(function(){
		var thisHref = $(this).attr("href");
		if(nowHref[1] == (thisHref)){
			$(this).closest('li.'+depth).find('a.'+depth).addClass("on");
		}
	});
}
//1depth click event
function depth1Click_m (evt) {

	var target = $(evt.currentTarget);
	
	if (target.hasClass("open")) {
		$("#mobileMenu").find('a.dept1').removeClass("open on");
		
		nowHrefFunction("dept1");
		
	} else {
		$("#mobileMenu").find('ul.dept2').hide(200);
		$("#mobileMenu").find('a.dept1').removeClass("open on");
		
		nowHrefFunction("dept1");
		
		target.addClass("open on");
		$(target).closest("li.dept1").find("ul.dept2").toggle(200);
	}
};

//2depth hover event
function depth2Hover_m (evt,depth) {
	var target = $(evt.currentTarget);
	
	if(target.hasClass("open")){
		$("#mobileMenu").find('ul.dept3').hide();
		$(target).parent().find("ul.dept3").show();
	}else{
		$("#mobileMenu").find('a.dept2').removeClass("open on");
		$("#mobileMenu").find('ul.dept3').hide();
		$(target).parent().find("ul.dept3").show();
		
		nowHrefFunction("dept2");

		if(depth != 'on'){
			target.addClass("open on");		
		}
	}
};

function depth2LiHover_m(evt,depth){
	var target = $(evt.currentTarget).find("a.dept2");
	
	if(target.hasClass("open")){
		$("#mobileMenu").find('ul.dept3').hide();
		$(target).parent().find("ul.dept3").show();
	}else{
		$("#mobileMenu").find('a.dept2').removeClass("open on");
		$("#mobileMenu").find('ul.dept3').hide();
		$(target).parent().find("ul.dept3").show();
		
		nowHrefFunction("dept2");

		if(depth != 'on'){
			target.addClass("open on");		
		}
	}
}
//3depth click event
function depth3Click_m (evt) {
	var target = $(evt.currentTarget);
	$("#mobileMenu").find('a').removeClass("on");
	$("#mobileMenu").find('ul.dept3').hide();
	
	$(target).closest("li.dept2").find("a.dept2").addClass("on");
	$(target).closest("li.dept1").find("a.dept1").addClass("on");
	
	target.toggleClass("on");
};

function depth3Leave_m (){
	$("#mobileMenu").find('a.dept2').removeClass("open on");
	$("#mobileMenu").find('ul.dept3').hide();
	
	nowHrefFunction("dept2");
}



jQuery(document).ready(function(){
	var $ = jQuery;
	//jquery start

	/* ***** common ***** */
  $(document).on("click", "a[href='#'], a[href='#none']", function(e){e.preventDefault();});



	/* gnb menu */
	$(".gnbMenu .gm").on("click", function(){
		var _this = $(this);

		if(_this.hasClass("on")){
			_this.removeClass("on");
			_this.siblings(".lnb").slideUp();
		}else{
			$(".gnbMenu .gm, .gnbMenu .mm, .gnbMenu .slm").removeClass("on");
			$(".gnbMenu .lnb, .gnbMenu .subm").slideUp();

			_this.addClass("on");
			_this.siblings(".lnb").slideDown();
		}
	});

	$(".gnbMenu .mm").on("click", function(){
		var _this = $(this);

		if(_this.hasClass("on")){
			_this.removeClass("on");
			_this.siblings(".subm").slideUp();
			_this.siblings(".subm").find(".slm").removeClass("on");
		}else{
			$(".gnbMenu .slm, .gnbMenu .mm").removeClass("on");
			$(".subm").slideUp();
			_this.addClass("on");
			_this.siblings(".subm").slideDown();
		}
	});

	$(".gnbMenu .slm").on("click", function(){
		var _this = $(this);

		if(_this.hasClass("on")){
			_this.removeClass("on");
		}else{
			_this.siblings().removeClass("on");
			_this.addClass("on");
		}
	});

	/*추가*/
	/*$(".gnbMenu .mm").hover(function(){
		var _this = $(this);

		if(_this.hasClass("on")){
			_this.removeClass("on");
			_this.siblings(".snb").show();
			_this.siblings(".snb").find(".sm").removeClass("on");
		}else{
			$(".gnbMenu .mm").removeClass("on");
			$(".snb").show();
			_this.addClass("on");
			_this.siblings(".snb").hide();
		}
	});
	*/

	$(".gnbMenu .mm").mouseover(function(){
		$(this).children(".snb").stop().slideUp();
	});

	$(".gnbMenu .mm").mouseleave(function(){
		$(this).children(".snb").stop().slideDown();
	});

	  $("#header [data-user='gnb-left']").on("click", function(){
		  var test=$(".rightCnt").width();
		  $(".leftCnt").toggleClass("on");
		 $('.scroll-pane').jScrollPane({}).resize();
	  });

	  $("#header [data-user='gnb-layer']").on("click", function(){
  		var _this =$(".gnbMenu");
 		if(_this.hasClass("on")){
 		   _this.stop().animate({"left":"-245px"}, 400, function(){
 			   _this.removeClass("on");
 			   $(".gnbMenu .gm, .gnbMenu .mm, .gnbMenu .slm").removeClass("on");
 			   $(".gnbMenu .lnb, .gnbMenu .subm").slideUp();
 		    });
 		}else{
 		   _this.stop().animate({"left":"0"}, 400, function(){
 			   _this.addClass("on");
 		   });
 	 	};
  	 });

	 $(".gnbMenu [data-user='gnb-close']").on("click", function(){
 		var _this =$(".gnbMenu");
		_this.stop().animate({"left":"-245px"}, 400);
		_this.removeClass("on");
		$("#dim").fadeOut(400, function(){
			$("html").removeClass("scroll-no");
		});
	 });

	 /* left menu */
	 $(".leftMenu .lmm1 .lms").on("click", function(){
 		var _this = $(this);

 		if(_this.hasClass("on")){
 			_this.removeClass("on");
 		}else{
 			_this.closest("li").siblings().find(".on").removeClass("on");
 			_this.addClass("on");
 		}
 	});

 	$(".leftMenu .lmm2 .lms").on("click", function(){
 		var _this = $(this);

    if(_this.siblings().hasClass("subm")){
      if(_this.hasClass("on")){
        _this.removeClass("on");
        _this.siblings(".subm").slideUp();
      }else{
        $(".leftMenu .subm").slideUp();
        _this.closest("li").siblings().find(".on").removeClass("on");
        _this.addClass("on");
        _this.siblings(".subm").slideDown();
      }
    }
    else{
      _this.removeClass("on");
    }
 	});

 	$(".leftMenu .subm .sm").on("click", function(){
 		var _this = $(this);

 		if(_this.hasClass("on")){
 			_this.removeClass("on");
 		}else{
 			$(".leftMenu .subm .sm").removeClass("on");
 			_this.addClass("on");
 		}
 	});

 	($(".leftMenu #lmmSort").length > 0) ? $( "#lmmSort" ).sortable({ revert: true }) :"";

	 $(".leftMenu .btn-lmm").on("click", function(){
		 $(this).toggleClass("on");
 		$(".leftMenu").toggleClass("on");
    $(".button_bottom").stop().delay(300).toggleClass("on");
 	 });

	 $(".leftMenu .btn-fav").on("click", function(){
		 $(this).toggleClass("on");
 	 });

	 $(".leftMenu .btn-set").on("click", function(){
		 $(this).toggleClass("on");
		 $(".leftMenu .lmm1").toggleClass("modi");
 	 });

	 /* side layer */
	 $("[data-user='aside']").on("click", function(){
		 var name = $(this).attr("data-username"),
		     top = $(window).scrollTop();

		$(".aside").stop().animate({"right":"-400px"}, 400);
		(!$(name).hasClass("reductAside"))? $(name).stop().animate({"right":"0"}, 500): $(name).stop().animate({"right":"-40"}, 500);

 	 });

	 $(".aside [data-user='aside-close']").on("click", function(){
		$(this).closest(".aside").stop().animate({"right":"-400px"}, 600);
		$(".btn-projt-set").stop().animate({"right":"-60px"}, 600);
	 });

	 $(".aside .instSelWrap .btn").on("click", function(){
		 $(this).next(".instBox").slideToggle();
	 });


	/* range - 오른쪽 영역 범례 */
	if($(".eff-report1").length > 0){
		var slider1 = new Slider('.eff-report1', {
			tooltip_position: "bottom",
			formatter: function formatter(val) {
				var txt = $('.eff-report1').attr("data-slider-tool");
				if (Array.isArray(val)) {
					return val[0] + " : " + val[1];
				} else {
					return val + " " + txt;
				}
			},
		});
	};

	if($(".eff-report2").length > 0){
		var slider2 = new Slider('.eff-report2', {
			tooltip_position: "bottom",
			formatter: function formatter(val) {
				var txt = $('.eff-report2').attr("data-slider-tool");
				if (Array.isArray(val)) {
					return val[0] + " : " + val[1];
				} else {
					return val + " " + txt;
				}
			},
		});
	};

	if($(".eff-report3").length > 0){
		var slider3 = new Slider('.eff-report3', {
			tooltip_position: "bottom",
			formatter: function formatter(val) {
				var txt = $('.eff-report3').attr("data-slider-tool");
				if (Array.isArray(val)) {
					return val[0] + " : " + val[1];
				} else {
					return val + " " + txt;
				}
			},
		});
	};

	if($(".eff-report4").length > 0){
		var slider4 = new Slider('.eff-report4', {
			tooltip_position: "bottom",
			formatter: function formatter(val) {
				var txt = $('.eff-report4').attr("data-slider-tool");
				if (Array.isArray(val)) {
					return val[0] + " : " + val[1];
				} else {
					return val + " " + txt;
				}
			},
		});
	};

  /*180410 레이어 팝업 추가*/
  if($(".eff-reportVol").length > 0){
    var slider4 = new Slider('.eff-reportVol', {
      tooltip_position: "bottom",
      formatter: function formatter(val) {
        var txt = $('.eff-reportVol').attr("data-slider-tool");
        if (Array.isArray(val)) {
          return val[0] + " : " + val[1];
        } else {
          return val + " " + txt;
        }
      },
    });
  };

	$(window).on("resize", function(){
		//scroll
		if($(".scroll-pane").length > 0){
			setTimeout(function() {
				var scrollPane = $('.scroll-pane').jScrollPane({});
			}, 200); //시간 200 이하로 수정하지 마세요. 왼쪽 확장영역과 관련 있어요.
		}

		//datepicker
		if($(".datepicker").length > 0){
			$( ".datepicker" ).datepicker({
				 showOn: "button",
				 //buttonImage: "../common/images/ico_calendar.gif",
				 buttonImageOnly: false,
				 buttonText: "Select date"
			   });
		}
	}).trigger("resize");


	/* ***** content ***** */
	/* tooltip */
	$('[data-toggle="tooltip"]').tooltip();

	$(".appCnt-inst .btn-list").on("click", function(){
		var _type1 = $(".appCnt-inst .inst-list.type1"),
		    _type2 = $(".appCnt-inst .inst-list.type2");
	   $(this).addClass("on").siblings().removeClass("on");
	   if($(this).hasClass("type1")){
		   _type1.show();
		   _type2.hide();
	   }else{
		   _type1.hide();
		   _type2.show();
	   }
	});

  var panelTop;

  //패널 확대 버튼
  $(".panel.type2 .btn-ico-zoom").on("click", function(){
    var _this = $(this);
    if(_this.hasClass("on")){
      $(window).scrollTop(panelTop);
      _this.removeClass("on").closest(".panel").removeClass("zoom").resize();
    $(".enVarSys").removeClass('zoomView').resize();
    }else{
      var panelTopTemp = $(window).scrollTop();
      panelTop = panelTopTemp;
      _this.addClass("on").closest(".panel").addClass("zoom").resize();
      var scrollPane = $('.panel.type2 .scroll-pane').jScrollPane({});
      $(window).scrollTop(0);
    }
  });

	//cBox slideToggle
	$(".cBox.cToggle-open .cBox-cnt").show();
	$(".cBox.cToggle [data-user='toggle']").on("click", function(){
		var _this = $(this).closest(".cBox.cToggle");
		if(! _this.hasClass("cToggle-open")){
			_this.addClass("cToggle-open").find(".cBox-cnt").slideDown();
		}else{
			_this.removeClass("cToggle-open").find(".cBox-cnt").slideUp();
		}
	});

  // Allcheck
    $(".checkAll").change(function() {
        $(".checkSel").prop('checked', $(this).prop("checked"));
    });

    $(".checkSel").change(function() {
        var allcount = $(".checkSel").length;
        var ckcount = $(".checkSel:checked").length;
        $(".checkAll").prop('checked', false);
        if (allcount == ckcount) {
            $(".checkAll").prop('checked', true);
        };
    });

  $("span.ico").on("mouseenter" , function(){
    if(!$(this).parents("div.btnTop")){
      $(this).addClass("on");
    }
  });

  $("span.ico").on("mouseleave" ,  function(){
    $(this).removeClass("on");
  });

  $(".btn-panel-add").on("mouseenter" , function(){
    $(this).parent("li").addClass("OnAdd");
  });

  $(".btn-panel-add").on("mouseleave" , function(){
    $(this).parent("li").removeClass("OnAdd");
  });

  $(".langSet > .dropdown-menu").on("mouseenter", function(){
    $(".langSet").find('> a').addClass("subOn");
  }),
  $(".langSet > .dropdown-menu").on("mouseleave", function(){
    $(".langSet").find('> a').removeClass("subOn");
  });

  // 파일첨부
  $("#fileInput").on('change', function(){  // 값이 변경되면

  		if(window.FileReader){  // modern browser
  			var filename = $(this)[0].files[0].name;
  		} else {  // old IE
  			var filename = $(this).val().split('/').pop().split('\\').pop();  // 파일명만 추출
  		}

  		// 추출한 파일명 삽입
  		$("#userfile").val(filename);
  	});


  $("#tutorial .sideMenu a").on("click", function(){
  	$("#tutorial .sideMenu li a").removeClass("on");
  	$(this).addClass("on");
  });

  //패널 내 앱 이름 변경
  $(".renameApp").on("click", function(){
    var nameP = $(this).parents('.panel_head').find('.tit');
    var name01 = nameP.text();//현재 앱 이름 저장
    //console.log(name01);
    nameP.contents().unwrap().wrap("<input type='text' name='renameApp' value='' class='form-control'></input>");
   $(this).parents(".panel_head").append("<div class='sdBtnWrap'><button type='button' name='button' class='btn btn-ico-saveName-s only-ico' title='이름저장'><span class='ico'>이름저장</span></button><button type='button' name='button' class='btn btn-ico-delName-s only-ico' title='변경취소'><span class='ico'>변경취소</span></button></div>");
    $("input[name='renameApp']").attr("placeholder", name01);
  });

  //연결 서비스 이름 변경
  $(".renameApp2").on("click", function(){
    var nameP = $(this).parents('.sevBox.on').find('.txt1');
    var name01 = nameP.text();//현재 앱 이름 저장
    //console.log(name01);
    nameP.contents().unwrap().wrap("<input type='text' name='renameSev' value='' class='form-control'></input>");
    $(this).parents(".sevBox").append("<div class='sdBtnWrap'><button type='button' name='button' class='btn btn-ico-saveName-s only-ico' title='이름저장'><span class='ico'>이름저장</span></button><button type='button' name='button' class='btn btn-ico-delName-s only-ico' title='변경취소'><span class='ico'>변경취소</span></button></div>");
    $("input[name='renameSev']").attr("placeholder", name01);
    $("input[name='renameSev']").css({"height":"38px"});
  });

  $(".renameApp3").on("click", function(){
    var nameP = $(this).parents('.panel_head').find('.tit');
    var name01 = nameP.text();//현재 앱 이름 저장
    //console.log(name01);
    nameP.contents().unwrap().wrap("<input type='text' name='renameServ' value='' class='form-control'></input>");
    $(this).parents(".panel_head").append("<div class='sdBtnWrap'><button type='button' name='button' class='btn btn-ico-saveName-s only-ico' title='이름저장'><span class='ico'>이름저장</span></button><button type='button' name='button' class='btn btn-ico-delName-s only-ico' title='변경취소'><span class='ico'>변경취소</span></button></div>");
    $("input[name='renameServ']").attr("placeholder", name01);
  });

  //자바 상세 인스턴스 이름 변경
  $(".renameInst").on("click", function(){
    var nameP = $(this).parents('.cBox-hd').find('.c-tit');
    var name01 = nameP.text();//현재 앱 이름 저장
    //console.log(name01);
    nameP.contents().unwrap().wrap("<input type='text' name='renameInst' value='' class='form-control'></input>");
    $(this).parents(".cBox-hd").append("<div class='sdBtnWrap'><button type='button' name='button' class='btn btn-ico-saveName-s only-ico' title='이름저장'><span class='ico'>이름저장</span></button><button type='button' name='button' class='btn btn-ico-delName-s only-ico' title='변경취소'><span class='ico'>변경취소</span></button></div>");
    $("input[name='renameInst']").attr("placeholder", name01);
  });

  //확대 상태의 환경변수 포틀릿 시스템정보 보기
  $(".enVar .btn.btn-more").on("click", function(){
    if($(this).parents(".panel.type2").hasClass("zoom"))
      {
        $(".enVarSys").toggleClass('zoomView');
        $(this).parents(".panel.type2").resize();
      }
  });

  $(".pull-right .btn.ico-view").on("click", function(){
    if($(this).hasClass("vOn"))
      {
        $(this).removeClass("vOn").css("opacity", "1");
      }
    else{
      $(this).addClass("vOn").css("opacity", "0.3");
    }
  });

  /* 시작/중지 버튼 토글 */
  $(".ico-start, .ico-pause").on("click", function(){
    $(this).toggleClass("ico-start ico-pause");
  });

  /* aside 내 드롭다운 박스 활성화 */
  $("#chkUse").on("click", function(){
    if($("#chkUse").prop("checked") == true){
      $("select[name='instOpt']").removeAttr("disabled");
    }
    else{
      $("select[name='instOpt']").attr("disabled", "true");
    }
  });

 $(window).load(progressAni());

  function progressAni(){
    var $progBar = $(".ul-prog .progressWrap.type1");
    var i; // index
    var j = $progBar.length;
    var progH = new Array(j);

    for(i = 0; i < j; i++)
    {
      progH[i] = $progBar.eq(i).find(".progress-bar").attr("data-original-title");

      $progBar.eq(i).find(".progress-bar").css("height", "0").stop().animate({"height":progH[i]}, 900);
    }
  };
	//jquery end
});

/*---------- 모바일 메뉴 확장 ----------*/
$(document).ready(function() {
   $('.m_btn_wrap').click(function(){
	 $('.m_menu_wrap').addClass('active');              
	 $('#wrap').addClass('scroll-no');              
   });
   $('.closed').click(function(){
	 $('.m_menu_wrap').removeClass('active');  
	 $('#wrap').removeClass('scroll-no');
   });
});


