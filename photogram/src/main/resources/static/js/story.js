/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

// (0) 현재 로그인한 사용자의 아이디
let principalId = $("#principalId").val();

//alert(principalId);


let page = 0;


// (1) 스토리 로드하기
function storyLoad() {

	$.ajax({
		url : `/api/image?page=${page}`,
		dataType : "json"
	}).done(res=>{
		console.log(res);
		
		res.data.content.forEach((image)=> {
			let storyItem = getStoryItem(image);
			$("#storyList").append(storyItem);
		});
		
	}).fail(error=>{
		console.log(error);		
	});
}

 storyLoad();

function getStoryItem(image) {
	let item= 
`<div class="story-list__item">
	<div class="sl__item__header">
		<div>
			<img class="profile-image" src="/upload/${image.user.profileImageUrl}"
				onerror="this.src='/images/person.jpeg'" />
		</div>
		<div>${image.user.username}</div>
	</div>
	
	<div class="sl__item__img">
		<img src="/upload/${image.postImageUrl}" />
	</div>
	
	<div class="sl__item__contents">
	
		<div class="sl__item__contents__icon">
			<button> `;
		
			if(image.likeState){
					item += `<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;				
			}else{
					item += `<i class="far fa-heart" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;								
			}	
		
		item += `
			</button>
		</div>
		
		<span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount}</b>likes</span>
		
		<div class="sl__item__contents__content">
			<p>${image.caption}</p>
		</div>
		
		<div id="storyCommentList-${image.id}">`;
		
		
		
		image.comments.forEach((comment)=>{
			
			item += `<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"">
								<p>
									<b>${comment.user.username} :</b> ${comment.content}
								</p>`;
							
						//로그인한 사람과 댓글 작성자와 아이디가 같다면 x 버튼 보이기
						if(principalId == comment.user.id){
							item += 
							    `<button onclick ="deleteComment(${comment.id})">
									<i class="fas fa-times"></i>
								</button>`;
						}		
								
								
							item += `
												</div>  `	
	});
							item += `		
												</div>
												
												<div class="sl__item__input">
													<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
													<button type="button" onClick="addComment(${image.id})">게시</button>
												</div>
											</div>
										</div>`

	return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
	//console.log("윈도우 scroll Top", $(window).scrollTop());
	//console.log("문서 높이 ", $(document).height());
	//console.log("윈도우 높이 ", $(window).height());
	
	let checkNum = $(window).scrollTop() - (  $(document).height() -  $(window).height()   );
	//console.log(checkNum);
	
	
	if(checkNum < 1 && checkNum > -1){
		page++;
		storyLoad();
	}
	
});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	
	let likeIcon = $(`#storyLikeIcon-${imageId}`);
	
	//빈 껍데기 하트일 때 -> 좋아요 하겠음  
	if (likeIcon.hasClass("far")) {
		
		$.ajax({
			type : "post",
			url : `/api/image/${imageId}/likes`,
			dataType : "json"
		}).done(res=>{
			
			//색깔 바꾸고 카운트 증가
			
			//아이디에 접근해서 내부에 있는 text를 가져온다.
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) + 1;
			//console.log("좋아요 카운터 : "+likeCount);
			$(`#storyLikeCount-${imageId}`).text(likeCount);
			
					
			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error=>{
			console.log("좋아요 처리 실패 ",error);
		});
	
		
		
	} else {
		//좋아요 취소 하겠음
		
		$.ajax({
			type : "delete",
			url : `/api/image/${imageId}/likes`,
			dataType : "json"
		}).done(res=>{
		
		
			//색깔 바꾸고 카운트 감소
			
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) - 1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);
		
		
			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(error=>{
			console.log("좋아요 취소 처리 실패 ",error);
		});
		

	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		imageId : imageId,
		content: commentInput.val()
	}

	// alert(data.content);  //작성한 댓글 확인

	//console.log(data); //기본적으로 받아오는 데이터 포맷 확인 -> 자바스크립트 형태임.
	//console.log(JSON.stringify(data)); //JSon으로 바꾼 데이터 포맷 확인
	
	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}
	
	$.ajax({
		
		type : "post",
		url : "/api/comment",
		data : JSON.stringify(data),
		contentType : "application/json; charset=utf-8", //보내는 타입
		dataType : "json" //받는 타입
		
	}).done(res=>{
		console.log("성공",res);
		
		let comment = res.data;
		
		let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
			    <p>
			      <b>${comment.user.username}</b>
			     : ${comment.content}
			    </p>
			    <button onclick ="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>
			  </div>
				`;
	
	//appent() -> 뒤에 넣음
	//prepend() -> 앞에 넣음 
	commentList.prepend(content);
		
	}).fail(error=>{
		//console.log("실패", error.responseJSON.date.content);
		alert(error.responseJSON.data.content);
	})

	commentInput.val("");	//input 필드를 비워준다.
}

// (5) 댓글 삭제
function deleteComment(commentId) {

	$.ajax({
		type : "delete",
		url : `/api/comment/${commentId}`,
		dataType : "json"
	}).done(res =>{
		console.log("성공", res);
		
		$(`#storyCommentItem-${commentId}`).remove(); 
		
		
	}).fail(error =>{
		console.log("실패", error);
	});

}







