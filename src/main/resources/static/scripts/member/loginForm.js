/**
 * 
 */
$(document).ready(function() {
	$('#errorEmail').html('');
	$('#errorPassword').html('');
	$('#errorLogin').html('');
	
	$('#floatingInput').keyup(emailCheck);
	$('#floatingPassword').keyup(passwordCheck);
	$('#loginbtn').click(loginCheck);
});

//이메일 입력시 체크해서 메세지 띄우는 함수
function emailCheck(){
	//console.log($('#floatingInput').val());
	if(!IsEmail($('#floatingInput').val())){
		$('#errorEmail').html('올바른 이메일 형식이 아닙니다');
	}
	else {
		$('#errorEmail').html('');
	}
}

//https://www.tutorialspoint.com/How-to-validate-email-using-jQuery
//에서 가져옴, 이메일 형식이 맞는지 체크해 주는 함수
function IsEmail(email) {
  var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
  if(!regex.test(email)) {
    return false;
  }else{
    return true;
  }
}

//비밀번호 입력시 체크해서 메세지 띄우는 함수
function passwordCheck(){
	if($('#floatingPassword').val().length < 8){
		$('#errorPassword').html('비밀번호는 8자리 이상 입니다');
	}
	else {
		$('#errorPassword').html('');
	}
}

//로그인 버튼 클릭시 ajax로 이메일과 비밀번호로 select
//값이 없다면 에러 메세지, 값이 있다면 summit();
//DB 구현 이후에 테스트 가능
function loginCheck(){
	//alert('안냥');
	let email = $('#floatingInput').val();
	let password = $('#floatingPassword').val();
	
	$.ajax({
			url: 'logincheck',
			type: 'post',
			data: {m_email: email, m_pw: password},
			dataType:'text',
			success: function(result){
				//alert("성공 : " + res);
				//select문에서 1혹은 0이 돌아올 것이다
				if(result){
					$('#loginForm').submit();
				}
				else{
					$('#errorPassword').html('메일 주소 혹은 비밀번호가 틀렸습니다');
				}
			},
			//controller쪽 구현이 안되어 있기 때문에 여기에서 테스트
			error: function(e){
				//alert("로그인 ajax 실패 : " + e.status);
				$('#errorLogin').html('메일 주소 혹은 비밀번호가 틀렸습니다');
			}
		});
}


//여유 되면 자동 로그인도 구현할 것