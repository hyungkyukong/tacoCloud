## 웹 요청 보안 처리하기

우리가 만들 프로젝트의 요구사항은 사용자를 인증해야 한다.
그러나 홈페이지, 로그인 페이지, 등록 ㅍ이지는 인증되지 않은 모든 사용자가 사용할 수 있어야 한다.

이런 보한 규칙을 구성하려면 SecurityConfig클래스에서 configure(HttpSecurity) 메서드를 오버라이딩 해야 한다.
```java
@Override
protected void configure(HttpSecurity http) throws Exception {
   ...
}
```

configure 인자인 HttpSecurity는 웹 수준에서 보안을 처리하는 방법을 구성하는데 사용된다.
해당 클래스를 사용해서 구성할 수 있는 것은 다음과 같다.

- HTTP 요청 처리를 허용하기 전에 충족되어야 할 특정 보안 조건을 구성한다.
- 커스텀 로그인 페이지를 구성한다.
- 사용자가 애플리케이션의 로그아웃을 할 수 있도록 한다.
- CSRF 공격으로부터 보호하도록 구성한다.

> 이 중에서 사용자가 보안 요구사항들을 충족하는지 확인 하는 방법을 지금부터 알아보자

###4.3.1 웹 요청 보안 처리하기

/design과 /orders 은 인증된 사용자에게만 허용하도록 기능을 추가해야 한다.

SecurityConfig.configure(HttpSecurity)메서드를 아래와 같이 변경 하자
```java
@Override
protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests().antMatchers("/design", "/orders").access("hasRole('ROLE_USER')")
			.antMatchers("/","/**","/h2-console/**").access("permitAll").and().httpBasic();
}
```

authrizeRequests는 ExpressionInterceptUrlRegistry 객체를 반환
이 객체는 URL 경로와 패턴 및 해당 경로의 보안 요구사항을 구성할 수 있다.
- /design과 /orders 요청은 ROLE_USER의 권한을 가져야만 허용된다.
- 이외 모든 사용자에게 허용

antMatchers는 지정된 경로이ㅡ 패턴 일치를 검사하고 여러번 사용 가능하다.
여러번 사용할 경우 순서가 중요하다. 앞에서 permitAll()이 있는 부분이 먼저 나왔다면
모든 오쳥의 사용자가자 접근 가능하게 되므로 /design, /orders 요청은 효력이 없어진다.

hasRole()과 permitAll()은 요청 경로의 보안 요구를 선언하는 메서드다.
아래 내용은 이때 사용 가능한 모든 메서드를 보여준다. 

| 메서드                       | 하는일                                                                                                |
|---------------------------|----------------------------------------------------------------------------------------------------|
| access(String)            | 인자로 전달된 SpEL 표현식이 true이면 접근을 허용한다.                                                                 |
| anonymous()               | 익명의 사용자에게 접근을 허용한다.                                                                                |
| authenticated()           | 익명이 아닌 사용자로 인증된 경우 접근을 허용한다.                                                                       |
| denyAll()                 | 무조건 접근을 거부한다.                                                                                      |
| fullyAuthenticated()      | 익명이 아니거나 또는 remember-me(바로 아래 참조)가 아닌 사용자로 인증되면 접근을 허용하낟.                                          |
| hasAnyAuthority(String..) | 지정된 권한 중 어떤 것이라도 사용자가 갖고 있으면 접근을 허용한다.                                                             |
| hasAnyRole(String..)      | 지정된 역할 중 하나라도 사용자가 갖고 있으면 접근을 허용한다.                                                                |
| hasAuthority(String)      | 지정된 권한을 사용자가 갖고 있으면 접근을 허용한다.                                                                      |
| hasIpAddress(String)      | 지정된 IP 주소로부터 요청이 오면 접근을 허용한다.                                                                      |
| hasRole(String)           | 지정된 역할을 사용자가 갖고 있으면 접근을 허용한다.                                                                      |
| not()                     | 다른 접근 메서드들의 효력을 무효화한다.                                                                             |
| permitAll()               | 무조건 접근을 허용한다.                                                                                      |
| rememberMe()              | remember-me(이전 로그인 정보를 쿠키나 데이터베이스로 저장한 후 일정 기간 내에 다시 접근 시 저장된 정보로 자동 로그인 됨)를 통해 인증된 사용자의 접근을 허용한다. |


