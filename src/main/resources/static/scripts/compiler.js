/**
 * 
 */
 $(document).ready(function() {
	//alert("connected");
	$('#runbtn').click(compile);
});

function compile(){
	//alert("click");
	let code = $('#editer').val();
	$.ajax({
		url: 'compile',
		type: 'post',
		data: {code: code},
		dataType:'text',
		success: function(res){
			//alert("성공 : " + res);
			$('#terminal').val(res);
		},
		error: function(e){
			alert("실패 : " + e);
		}
	});
}