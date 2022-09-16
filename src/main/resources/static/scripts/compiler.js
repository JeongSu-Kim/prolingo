/**
 * 
 */
 $(document).ready(function() {
	//alert("connected");
	$('.languageSelect').change(languageSelect);
	$('#runbtn').click(compile);
});

function languageSelect(){
	if($(this).val() == 'java'){
		$('#editer').val(
			"class Testjava{\n"
			+"\tpublic static void main(String[] args) {\n"
			+"\t\tSystem.out.println(\"Hello World!\");\n"
			+"\t}\n"
			+"}");
	}
	else if($(this).val() == 'c'){
		$('#editer').val(
			"#include <stdio.h>\n\n"
			+"int main() {\n"
			+"\tprintf(\"Hello World!\\n\");\n"
			+"\treturn 0;\n"
			+"}");
	}
	
	//alert($('.languageSelect:checked').val());
}

function compile(){
	//alert("click");
	let language = $('.languageSelect:checked').val();
	let code = $('#editer').val();
	$.ajax({
		url: 'compile',
		type: 'post',
		data: {language: language, code: code},
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