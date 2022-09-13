/**
 * 업척 출력을 위한 ajax 실행문
 */
 
 $(document).ready(function() {
		
		achievements();
	});
	
	function achievements(){
		
		console.log('업적함수 실행');
			
		$.ajax({
			url:'achievelist',
			type:'get',
			dataType:'json',
			success: output,
			error: function(e){
				console.log(JSON.stringify(e));	
			}
		});
	}
	
	function output(ob){
		
		console.log(ob);
		
		let s = '';
		
		$.each(ob, function(index ,item){
			s += '<div class="list-group-item list-group-item-action d-flex gap-3 py-3" aria-current="true">';
			s += '<img th:src=' + item.A_icon + 'alt="twbs" width="50" height="50" class="rounded-circle flex-shrink-0">';
			s += '<div class="d-flex gap-2 w-100 justify-content-between">';
			s += '<div><h6 class="mb-0">' + item.A_name + '</h6>';
			s += '<p class="mb-0 opacity-75">' + item.A_text + '</p></div>';
			s += '<small class="opacity-50 text-nowrap">' + item.A_date + '</small></div></div>';
		});
		$('#output').html(s); 
		
	}