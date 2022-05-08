package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cos.photogramstart.domain.user.subscribe.Subscribe;
import com.cos.photogramstart.domain.user.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service 
public class SubscribeService {

	
	private final SubscribeRepository subscribeRepository;
	
	
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
