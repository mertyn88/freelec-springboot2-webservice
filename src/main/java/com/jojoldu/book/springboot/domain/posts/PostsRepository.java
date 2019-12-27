package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/*
    DAO라고 불리는 DB Layer접근자를 JPA에서는 Repository라고 부르며 인터페이스로 생성한다.
    단순히, 인터페이스를 생성 후, JpaRepository<Entity 클래스,PK 타입>을 상속하면 기본적인 CRUD 메소드가 자동으로 생성된다.
    *주의 >> Entity클래스와 기본 Entity Repository는 함께 위치해야한다.

 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

}
