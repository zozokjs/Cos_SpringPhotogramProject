// (1) 회원정보 수정
function update(userId) {



	//serialize()를 이용해 id가 profileUpdate인 form 태그 내부의 모든 info 값을 data에 저장함.
	let data = $("#profileUpdate").serialize();
	
	
	console.log(data);
	
	$.ajax({
		
		type : "put",
		url : `/api/user/${userId}`,
		data : data,
		contentType:"application/x-www-form-urlencoded; charset=utf-8",
		dataType:"json"
		
	}).done(res=>{
		
		alert("update 성공");		
		location.href=`/user/${userId}`;
	}).fail(error=>{
		
		console.log(" update 실패");		

	});
	
}