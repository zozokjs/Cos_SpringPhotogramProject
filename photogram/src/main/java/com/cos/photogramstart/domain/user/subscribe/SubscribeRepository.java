package com.cos.photogramstart.domain.user.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer>{


	@Modifying //INSERT, DELETE, UPDATE를 네이티브 쿼리로 작성하려면 이 어노테이션이 필요함.
	//:fromUserId에서  : 는 아래 메소드의 매개변수로 들어오는 fromUserId를 쿼리에 넣겠다는 의미임. 정해진 양식.
	@Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())",
			nativeQuery = true)
	void mSubscribe(int fromUserId, int toUserId);
	
	@Modifying
	@Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId",
			nativeQuery = true)
	void mUnSubscribe(int fromUserId, int toUserId);
	
}
