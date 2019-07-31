/*******************************************************************************
 * 파일명 : index.js
 * 제작자 : hm kim
 * 일  자 : 2018-05-09
 * 내  용 : content script
 ******************************************************************************/

jQuery(document).ready(function(){
	
	// 펼치기 숨기기
	$('.btn.only-ico').click(function (e) {
		$this = $(this);
		document.this = $this;
		
		// 작은 화살표
		if($this.hasClass("ico-right")){ // 오른쪽 누르면 펼치기
			$this.removeClass("ico-right").addClass("ico-left");
			$this.parent().parent().parent().parent().find("ul.domainList").first().css( "display", "block" );
		}
		else if($this.hasClass("ico-left")){ // 왼쪽 누르면 숨기기
			$this.removeClass("ico-left").addClass("ico-right");
			$this.parent().parent().parent().parent().find("ul.domainList").first().css( "display", "none" );
		}
		
		// 큰 화살표
		else if($this.hasClass("ico-big-right")){ // 오른쪽 누르면 펼치기
			$this.removeClass("ico-big-right").addClass("ico-big-left");
			$this.parent().parent().find("ul.domainList").first().css( "display", "block" );
		}
		else if($this.hasClass("ico-big-left")){ // 왼쪽 누르면 숨기기
			$this.removeClass("ico-big-left").addClass("ico-big-right");
			$this.parent().parent().find("ul.domainList").first().css( "display", "none" );
		}
		
	});
	
	/*
	// 어사이드 저장 버튼의 attr 에 추가할 위치 ID 넣기
	$(".btn-service-add").on("click", function(){
		 var id = $(this).attr("id"),
		$("#domainAddBtn").attr("id", id);
	 });
	// 어사이드 저장 버튼 누르면 도매인 추가
	$('#domainAddBtn').click(function (e) {
		var btnId = $(this).attr("id");
		var domain = $("#domainAddText1").val() +$("#domainAddText2").text();
		var li = '<li>'+
				'<div class="panelWrap">'+
					'<div class="panel type2">'+
						'<div class="panel_head noBorder">'+
							'<div class="tit pull-left">Company 1</div>'+
							'<div class="pull-right">'+
								'<div class="dropdown">'+
									'<button type="button" class="btn only-ico ico-dot" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="실행 설정">'+
										'<span class="ico">실행 설정</span>'+
									'</button>'+
									'<ul class="dropdown-menu" role="btn-option" aria-labelledby="btn-option">'+
										'<li><a href="#" data-user="aside" data-username="#aside-domainEdit">도메인 수정</a></li>'+
										'<li><a href="#">도메인 삭제</a></li>'+
										'<li><a href="#" data-user="aside" data-username="#aside-ipConnetion">접속IP 해제</a></li>'+
									'</ul>'+
								'</div>'+
							'</div>'+
						'</div>'+
						'<div class="panel_body noPadding">'+
							'<p class="ment">Access IP : <br/>Domain : '+ domain +'</p>'+
							'<button type="button" class="btn more"><span class="state"><span class="stop">정지</span><span class="detail">STOPPED</span></span></button>'+
						'</div>'+
					'</div>'+
				'</div>'+
			'</li>';
			
		//$this.parent().before(li);
		$("#".btnId).before(li);
		
		// side layer 재정의
		 $("[data-user='aside']").on("click", function(){
			 var name = $(this).attr("data-username"),
				 top = $(window).scrollTop();
			$(".aside").stop().animate({"right":"-400px"}, 400);
			(!$(name).hasClass("reductAside"))? $(name).stop().animate({"right":"0"}, 500): $(name).stop().animate({"right":"-40"}, 500);
		 });
	});*/
	
	//jquery end
});
