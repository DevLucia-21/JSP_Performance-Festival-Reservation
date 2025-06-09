<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원가입</title>
</head>
<body>
    
    <!-- 상단 PerFortival -->
    <h1 style="font-size: 2rem; font-weight: bold; cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/main'">
        PerFortival
    </h1>
    <hr>
    
    <h2>회원가입</h2>
    <form action="<%= request.getContextPath() %>/member/signup" method="post" onsubmit="return validateForm()">
        아이디:
        <input type="text" name="id" id="idInput" required>
        <button type="button" onclick="checkId()">중복확인</button>
        <span id="checkResult" style="font-weight: bold;"></span>
        <br><br>
        
        비밀번호: <input type="password" name="pw" minlength="8" required><br><br>
        이름: <input type="text" name="name" required><br><br>
        이메일: <input type="email" name="email" required><br><br>
				주소: <input type="text" id="address" name="address" readonly required>
				<button type="button" onclick="execDaumPostcode()">주소 찾기</button>
				<br><br>
        
        
        <input type="submit" value="가입">
    </form>

    <script>
	    let isIdChecked = false; // 중복 확인 여부 저장
	
	    function checkId() {
	        const id = document.getElementById('idInput').value;
	
	        if (!id) {
	            alert("아이디를 입력해주세요.");
	            return;
	        }
	
	        fetch('<c:url value="/member/checkId" />?id=' + encodeURIComponent(id))
	            .then(res => res.json())
	            .then(data => {
	                const resultSpan = document.getElementById("checkResult");
	                if (data.duplicate) {
	                    resultSpan.innerText = "이미 사용 중인 아이디입니다.";
	                    resultSpan.style.color = "red";
	                    isIdChecked = false;
	                } else {
	                    resultSpan.innerText = "사용 가능한 아이디입니다.";
	                    resultSpan.style.color = "green";
	                    isIdChecked = true;
	                }
	            })
	            .catch(error => {
	                console.error("중복 확인 오류:", error);
	                alert("오류가 발생했습니다.");
	            });
	    }
	
	    function validateForm() {
	        if (!isIdChecked) {
	            alert("아이디 중복 확인을 해주세요.");
	            return false;
	        }
	        return true;
	    }
	    </script>
	    
	    <!-- 카카오 주소 API 스크립트 -->
			<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
			<script>
			function execDaumPostcode() {
			    new daum.Postcode({
			        oncomplete: function(data) {
			            document.getElementById('address').value = data.address;
			        }
			    }).open();
			}
			</script>
</body>
</html>
