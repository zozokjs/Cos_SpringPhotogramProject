package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cos.photogramstart.domain.user.subscribe.Subscribe;
import com.cos.photogramstart.domain.user.subscribe.SubscribeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service 
public class SubscribeService {

	
	private final SubscribeRepository subscribeRepository;
	
	
	@Transactional
	public void 구독하기(int fromUserId, int toUserId) {
		//subscribeRepository.save(null);
		subscribeRepository.mSubscribe(fromUserId, toUserId);
	
	}

	
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);

	}
	
	
	
}
