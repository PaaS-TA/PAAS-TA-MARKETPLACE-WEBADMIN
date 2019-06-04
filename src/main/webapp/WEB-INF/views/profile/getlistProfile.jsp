<%--
  Created by IntelliJ IDEA.
  User: hrjin
  Date: 2019-05-13
  Time: 오후 1:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ page import="org.openpaas.paasta.marketplace.web.seller.common.Constants" %> --%>
<html>
<head>
    <meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
    <title>프로필 상세 조회</title>

    <%@include file="../common/commonLibs.jsp" %>
</head>
<body>
<table>
	<thead>
	<tr>
        <td>판매자명</td>
        <td>유형</td>
        <td>담당자명</td>
        <td>메일 주소</td>
        <td>홈페이지 주소</td>
        <td>등록일</td>
	</tr>
	</thead>
	<tbody id="profileListArea">
<!--     <tr>
        <td>
            <span class="sellerName"></span>
        </td>
        <td>
            <span class="businessUnit"></span>
        </td>
        <td>
            <span class="managerName"></span>
        </td>
        <td>
            <span class="emailAddress"></span>
        </td>
        <td>
            <span class="homepageUrl"></span>
        </td>
        <td>
            <span class="createdDate"></span>
        </td>
    </tr> -->
    </tbody>
</table>
</body>
</html>
<script type="text/javascript">

	var PROFILE_LIST;
	var PROFILE_LIST_LENGTH = 0;
	

    var getProfileList = function() {
        var reqUrl = "<%= SellerConstants.URI_CTRL_SELLER_PROFILE_LIST %>";
        console.log("url 은 ???" + reqUrl);

        procCallAjax(reqUrl, "GET", null, null, callbackGetProfileList);
    };

    var callbackGetProfileList = function(data){
        console.log("프로필 목록 ::: " + JSON.stringify(data));
        
        PROFILE_LIST = data;
        PROFILE_LIST_LENGTH = data.length;
        setProfileList("");
    };

    var setProfileList = function (searchKeyword) {
        var resultListArea = $('#profileListArea');
        var checkListCount = 0;
        var htmlArray = [];
        var profileList = [];
        var profileOne;

        $.each(PROFILE_LIST, function(index, item) {
            var sellerName = item.sellerName;
            alert(JSON.stringify(item));
            profileList.push(item);

        	htmlArray.push(
        		'<tr data-search-key="' + sellerName + '">'
                + '<td><span>' + item.businessType + '</span></td>'
                + '<td><span>' + item.managerName + '</span></td>'
                + '<td><span>' + item.email + '</span></td>'
                + '<td><span>' + item.homepageUrl + '</span></td>'
                + '<td><span>' + item.strCreateDate + '</span></td>'
                + '</tr>');
            checkListCount++;
        });

        if (PROFILE_LIST_LENGTH < 1 || checkListCount < 1) {
        	resultListArea.hide();
        } else {
        	resultListArea.html(htmlArray);
            resultListArea.show();
        }
    };

    
    // ON LOAD
    $(document).ready(function() {
    	getProfileList();
    });
</script>