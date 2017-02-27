<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.request.contextPath }/assets/css/guestbook.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	var dialogDeleteForm = null;
	var isEnd = false;
	var page = 0;
	var render = function(vo, prepend) {
		var html = "<li id='li-" + vo.no + "'>" + 
				   "<strong>" + vo.name + "</strong>" + 
				   "<p>" + vo.content + "</p>" + 
				   "<strong>" + vo.regDate + "</strong>" + 
				   "<a href='' title='삭제' data-no='" + vo.no + "'>삭제</a>" + "</li>";
		if (prepend == true) {
			$("#list").prepend(html);
		} else {
			$("#list").append(html);
		}
	}
	
	var fetchList = function() {
		if (isEnd == true) {
			return;
		}
		
		++page;
		// ajax 통신
		$.ajax({
			url : "/mysite2/api/guestbook/list/" + page,
			type : "get",
			dataType : "json",
			data : "",//post일땐 data에 입력
			//  contentType: "application/json",
			success : function(response) {
				if (response.result != "success") {
					console.log(response.message);
					return;
				}

				if (response.data.length == 0) {
					isEnd = true;
					return;
				}

				$(response.data).each(function(index, vo) {
					render(vo, false);
				});
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}

		});
	}

	$(function(){
		$( "#write-form" ).submit( function(event){
			// 폼의 submit 기본 이벤트 처리를 막는다.
			event.preventDefault();
			
			/* ajax 입력 */
			$.ajax( {
				url : "/mysite2/api/guestbook/add",
				type: "post",
			    dataType: "json",
			    data: "name=" + $("input[name='name']").val() + "&" + 
			          "password=" + $("input[name='password']").val() + "&" +
			          "content=" + $("textarea").val(),
			    success: function( response ){
					console.log( response );
					render( response.data, true );
			    },
			    error: function( XHR, status, error ){
			       console.error( status + " : " + error );
			   	}
		    });
			
			return false;
		});
		$(window).scroll(function() {
			var $window = $(this);
			var scrollTop = $window.scrollTop();
			var windowHeight = $window.height();
			var documentHeight = $(document).height();

			if (scrollTop + windowHeight + 10 > documentHeight) {
				fetchList();
			}
		});

		//삭제 버튼 클릭 이벤트 매핑(Live Event Mapping)
		$(document).on("click", "#list li a", function(event) {
			event.preventDefault();
			var $a = $(this);
			var no = $a.attr("data-no");
			$("#no-delete").val(no);
			dialogDeleteForm.dialog("open");
		});

		dialogDeleteForm = $("#dialogDeleteForm").dialog({
			autoOpen : false,
			height : 200,
			width : 350,
			modal : true,
			buttons : {
				"삭제" : function() {
					var no = $("#no-delete").val();
					var password = $("#password-delete").val();
					/* ajax 입력 */
					$.ajax( {
						url : "/mysite2/api/guestbook/delete",
						type: "post",
					    dataType: "json",
					    data: "no=" + no + "&password=" + password,
					    success: function( response ){
					    	console.log(response);
					    	if(response.result != "success"){
					    		console.log(response.message);
					    		dialogDeleteForm.dialog("close");
					    		return;
					    	}
					    	//삭제 실패
					    	if(response.data == -1) {
					    		$( "#delete-tip-normal" ).hide();
					    		$( "#delete-tip-error" ).show();
					    		$( "#password-delete" ).val("").focus();
					    		return;
					    	}
					    	
					    	//삭제 성공
					    	
					    	//li 엘리멘트 삭제
					    	$( "#li-" + response.data).remove();
					    	
					    	$( "#delete-tip-normal" ).show();
					    	$( "#delete-tip-error" ).hide();
					    	$( "#password-delete" ).val("");
					    	
					    	//다이얼로그 닫기
					    	dialogDeleteForm.dialog("close");
				    		return;
					    
					    },
					    error: function( XHR, status, error ){
					       console.error( status + " : " + error );
					   	}
				    });
				},
				"취소" : function() {
					$(this).dialog("close");
				}
			},
			close : function() {
			}
		});

		//첫페이지 로딩
		fetchList();
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="write-form" action="" method="post">
					<table>
						<tr>
							<td>이름</td>
							<td><input type="text" name="name"></td>
							<td>비밀번호</td>
							<td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 보내기 "></td>
						</tr>
					</table>
				</form>
				<ul id="list"></ul>

			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp" />
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>

	<div id="dialogDeleteForm" title="Delete Message" style="display:none">
		<p id="delete-tip-normal" style="padding:20px 0; font-weight:bold; font-size:14px;">삭제를 원하시면 비밀번호를 입력해 주세요.</p>
		<p id="delete-tip-error" style="padding:20px 0; font-weight:bold; font-size:14px; color:#F15F5F; display:none">비밀번호가 틀렸습니다.<br>다시 입력해 주세요.</p>
		<form>
			<label for="password">비밀번호		</label>
			<input type="hidden" id="no-delete" value=""/>
			<input type="password" name="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
			<input type="submit" tabindex="-1" style="position: absolute; top: -1000px">
		</form>
	</div>
</body>
</html>