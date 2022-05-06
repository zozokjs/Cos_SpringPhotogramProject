package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//어노테이션이 없어도 ioc 등록이 자동으로 된다. jparepository를 상속 했다면.
public interface UserRepository extends JpaRepository<User, Integer>{

	//JPA QUERY METHOD 양식을 써서 메소드 생성함. db와 통신하려면 정해진 양식 써야 됨
	User findByUsername(String username);
}
