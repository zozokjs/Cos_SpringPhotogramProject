package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service 
public class SubscribeService {

	
	private final SubscribeRepository subscribeRepository;
	
	//모든 Repository는 EntityManager의 구현체
	private final EntityManager em;

	
	@Transactional(readOnly=true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId){
		
		
		/*스칼라 서브쿼리 추가
		 * -> 반드시 한 줄 끝날 때마다 1칸의 공백이 있어야 함
		 * -> 쿼리 끝에 세미콜론 추가 절대 안 됨
		 * */
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		sb.append("if((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, ");
		sb.append("if((? = u.id), 1, 0) equalUserState ");
		sb.append("FROM USER u INNER JOIN subscribe s ");
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId = ?"); 

		//첫번째, 두번째 물음표에는 로그인한 아이디  principalId
		//마지막 물음표에는 pageUserId  
		
		//물음표에 파라미터 세팅
		Query query = em.createNativeQuery(sb.toString())
					.setParameter(1, principalId)
					.setParameter(2, principalId)
					.setParameter(3, pageUserId);
									
		
		//쿼리 실행(쿼리 결과를 자바 클래스(여기선 DTO)에 매핑하기 위해 QLRM 라이브러리 사용함)
		JpaResultMapper result = new JpaResultMapper();
		//query를 실행하여 SubscribeDto 형태로 받되, 리스트 형식으로 받음.
		List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);
		
		return subscribeDtos;
		
	}
	
	
	
	@Transactional
	public void 구독하기(int fromUserId, int toUserId) {
		
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		} catch (Exception e) {
			throw new CustomApiException("이미 구독 하였습니다.");
		}
		//이미 구독 되어 있음
		//자기 자신을 구독 할 수 없음
		
		
	
	}

	
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);

	}
	
	
	
}
