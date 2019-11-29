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
	
});
