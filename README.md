# 스프링 시큐리티 구조
---
![스크린샷 2023-12-21 171426](https://github.com/bizplus033/jinwaterpractice/assets/154400053/c8f6f6f0-223e-49f8-89e6-e9291498e79e)
- `UserService`는 `UserDetailsService`를 구현하고 있다.
- 해당 인터페이스를 구현하면 무조건 `loadUserByUsername`을 구현해줘야 한다.
- 해당 메소드 안에서 `UserDetails`의 구현체를 리턴해야 한다.
- `UserDetails` 의 구현체는 스프링 시큐리티에서 이미 `User`라는 구현체를 제공해준다.
- 그 `User`를 구현한 `UserAccount`에 우리 프로젝트의 User 엔티티를 포함 시켜서 리턴할 것

# User 도메인 querydsl 구조
---
![userLog_querydsl](https://github.com/bizplus033/jinwaterpractice/assets/154400053/b3562b1b-8951-4947-905e-1d05dfd9eed1)
- `JpaUserRepository` 인터페이스가 `JpaRepository`와 `DslUserRepository`를 상속받는다.
- `DslUserRepository` 인터페이스는 구현해줘야 한다.
- 그 구현체가 `DslUserRepositoryImpl`

# UserLog 도메인 querydsl 구조
---
![userlog_querydsl](https://github.com/bizplus033/jinwaterpractice/assets/154400053/d2ce1a36-b816-4408-b576-90b9377719fe)
- `User` 도메인과는 다르게
  - `JpaRepository`를 상속받는 인터페이스 따로
  - `querydsl`를 사용하는 클래스 따로
- 이렇게 나누는게 더 좋을 수도 있다.

  
