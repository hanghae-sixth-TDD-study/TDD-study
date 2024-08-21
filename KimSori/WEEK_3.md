## TDD 3주차 - 5, 6장 요약

### JUnit 5 구성요소

1. JUnit 플랫폼: 테스팅 프레임워크를 구동하기 위한 런처와 테스트 엔진을 위한 API 제공
2. Junit 주피터(Jupiter): JUnit 5 를 위한 테스트 API와 실행 엔진을 제공
3. JUnit 빈티지(Vintage): JUnit 3과 4로 작성된 테스트를 JUnit 5 플랫폼에서 실행하기 위한 모듈 제공



### 주요 단언 메서드

| 메서드                                                     | 설명                                                         |
| ---------------------------------------------------------- | ------------------------------------------------------------ |
| assertEquals(expected, actual)                             | 실제 값(actual) 이 기대하는 값(expected) 과 같은지 검사      |
| assertNotEquals(unexpected, actual)                        | 실제값(actual)이 특정 값(unexpected) 과 같지 않은지 검사     |
| assertSame(Object expected, Object actual)                 | 두 객체가 동일한 객체인지 검사                               |
| assertNotSame(Object unexpected, Object actual)            | 두 객체가 동일하지 않은 객체인지 검사                        |
| assertTrue(boolean condition)                              | 값이 true 인지 검사                                          |
| assertFalse(boolean condition)                             | 값이 false 인지 검사                                         |
| assertNull(Object actual)                                  | 값이 null 인지 검사                                          |
| assertNotNull(Object actual)                               | 값이 null이 아닌지 검사                                      |
| fail()                                                     | 테스트를 실패 처리. 테스트에 실패했음을 알리고 싶을 때 사용  |
| assertThrows(Class<T> expectedType, Executable executable) | executable 을 실행한 결과로 지정한 타입의 익셉션이 발생하는지 검사 |
| assertDoesNotThrows(Executable executable)                 | executable 을 실행한 결과로 익셉션이 발생하지 않는지 검사    |



### JUnit 테스트 메서드 실행 순서

1. 테스트 메서드를 포함한 객체 생성
2. (존재하면) **@BeforeEach** 애노테이션이 붙은 메서드 실행
3. @Test 애노테이션이 붙은 메서드 실행
4. (존재하면) **@AfterEach** 애노테이션이 붙은 메서드 실행



### 모든 테스트 실행 방법

1. mvn test (래퍼를 사용하는 경우 mvnw test)
2. gradle test (래퍼를 사용하는 경우 gradle test)



### 애노테이션 종류

#### @Test

* @Test 애노테이션을 붙인 메소드를 private 면 안된다.



#### @BeforeEach

* 테스트 실행하는데 필요한 준비 작업 할 때 사용
* 테스트에서 사용한 임시 파일 생성하거나 테스트 메서드에서 사용할 객체 생성



#### @AfterEach

* 테스트 실행 후 정리할 것이 있을때 사용
* 테스트에서 사용한 임시 파일 삭제해야 할 때 사용



#### @BeforeAll

* 한 클래스의 모든 테스트 메서드가 실행되기 전에 특정 작업을 수행해야 하는 경우 사용
* 정적 메서드에 적용
* 클래스의 모든 테스트 메서드를 실행하기 전에 한 번 실행



#### @AfterAll

* 클래스의 모든 테스트 메서드를 실행한 뒤 실행
* 정적 메서드에 적용



#### @DisplayName("이름")

* 테스트 표시 이름 붙이기



#### @Disabled

* 특정 테스트를 실행하지 않고 싶은 경우 사용



### 유의사항

* 각 테스트 메서드를 서로 **독립적**으로 동작해야 함
  * 한 테스트 메서드의 결과에 따라 다른 테스트 메서드의 실행 결과가 달라지면 안된다.
  * 따라서 테스트 메서드가 서로 필드를 공유한다거나 실행 순서를 가정하고 테스트 작성 하지 말기.



### 테스트 코드 구성요소

* 상황 (given)
* 실행 (when)
* 결과확인 (then)
* 각 테스트 메서드마다 객체 생성해서 상황 설정

```java
@Test
void exactMatch(){
    // 정답이 456인 상황
    BaseballGame game = new BaseballGame("456");
    // 실행
    Score score = game.guess("456");
    // 결과 확인
    assertEquals(3, score.strikes());
    assertEquals(0, score.balls());
}
```

* @BeforeEach 적용한 메서드에서 상황 설정

```java
private BaseballGame game;

@BeforeEach
void givenGame(){
    game = new BaseballGame("456");
}

@Test
void exactMatch(){
    // 실행
    Score score = game.guess("456");
    // 결과 확인
    assertEquals(3, score.strikes());
    assertEquals(0, score.balls());
}
```

* 상황이 없는 경우

```java
@Test
void meetsAllCriteria_Then_Strong(){
    // 실행
    PasswordStrengthMeter meter = new PasswordStrengthMeter();
    PasswordStrength result = meter.meter("ab12!@AB");
    // 결과 확인
    assertEquals(PasswordStrength.STRONG, result);
}
```

* 상황 - 실행 - 결과 구조에 너무 집착하지 않아도 된다!
* 외부에서 파일을 만들어 테스트 하는 방법도 있다
  * 결과 데이터 파일화
  * 데이터 파일화 해서 보관 후 테스트



