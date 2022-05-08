// (1) 회원정보 수정
function update(userId, event) {

	event.preventDefault(); //form 태그 액션을 막음.

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
		//HTTP STATUS가 200번대일 때 성공 처리. 그게 아니면 FAIL 처리 됨.
		console.log("성공", res);
		alert("update 성공");		
		location.href=`/user/${userId}`;
	}).fail(error=>{
		
		if(error.data == null){
			alert(error.responseJSON.message);
		}else{
			console.log(" update 실패", error);	 		
			//이렇게 하니까 알림창에 [Object Object]라고 나옴
			//alert(error.responseJSON.data.name);
	
			//그래서 자바스크립트 문자열을 JSON.stringfy()로 JSON 문자열로 변환함 
			alert(JSON.stringify(error.responseJSON.data));
		
		}
	})
	
}