## TDD 4주차 - 7, 8장 요약

### 대역의 필요성

* 테스트 대상 변수가 변동 가능성이 크거나 실값이 제공되기 까지의 기간이 길어지게 된다면 테스트 실 값을 대신할 **대역**을 이용해 테스트를 진행 할 수 있다.



### 대역의 종류

| 대역 종류   | 설명                                                         |
| ----------- | ------------------------------------------------------------ |
| 스텁(Stub)  | 구현을 단순한 것으로 대체한다. 테스트에 맞게 단순히 원하는 동작을 수행한다. StubCardNumberValidator 가 스텁 대역에 해당 |
| 가짜(Fake)  | 제품에는 적합하지 않지만, 실제 동작하는 구현을 제공한다. DB 대신에 메모리를 이용해서 구현한 MemoryAutoDebitInfoRepository가 가짜 대역에 해당. |
| 스파이(Spy) | 호출한 내역을 기록한다. 기록한 내용은 테스트 결과를 검증할 때 사용한다. 스텁이기도 하다. |
| 모의(Mock)  | 기대한 대로 상호작용하는지 행위를 검증한다. 기대한 대로 동작하지 않으면 익셉션을 발생할 수 있다. 모의 객체는 스텁이자 스파이도 된다. |



### 모의 객체 유의사항

* 모의 객체는 스텁과 스파이 지원. 
  * 과하게 사용시 테스트 코드 복잡성 증대



### 테스트 어려운 코드 

* 하드 코딩된 경로

```java
Path path = Paths.get("D:\\data\\pay\\cp0001.csv")
```

* 의존 객체를 직접 생성
  * 필요 모든 환경 구성, DB, table 준비 필요하기 때문
  * 중복 데이터 삽입 문제 발생 up

```java
private PayInfoDao payInfoDao = new PayInfoDao();
```

* 정적 메소드 사용
  * 인증 서버 필요한 경우 테스트 환경 맞추기 필요

```java
boolean authorized = AuthUtil.authorize(authKey);
```

* 실행 시점에 따라 달라지는 결과
  * 신뢰성 깨짐

```java
LocalDate now = LocalDate.now();
```

* 역할이 섞여있는 코드
* 메서드 중간에 소켓 통신 코드 포함
* 콘솔에서 입력받거나 결과를 콘솔에 출력
* 테스트 대상이 사용하는 의존 대상 클래스나 메서드가 final. 이 경우 대역으로 대체가 어려울 수 있음
* 테스트 대상의 소스를 소유하고 있지 않아 수정 어려움



### 테스트 가능한 설계

* 하드코딩된 상수를 생성자나 메서드 파라미터로 받기

```java
public class PaySync{
	private String filePath = "D:\\data\\pay\\cp0001.csv";
    
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }
    
    public void sync() throws IOException{
        Path path = Paths.get(filePath);
        // ...
    }
}
```

```java
@Test
void someTest() throws IOException{
    PaySync paySync = new PaySync();
    paySync.setFilePath("src/test/reousrces/cp0111.csv");
    
    paySync.sync();
    // ... 결과 검증
}
```

* 의존 대상 주입 받기

  * 생성자를 통해 의존 대상 주입

  ```java
  public PaySync(PayInfoDao payInfoDao){
  	this.payInfoDao = payInfoDao;
  }
  ```

  * 세터를 통해 의존 대상 교체
  
  ```java
  public void setPayInfoDao(PayInfoDao payInfoDao){
  	this.payInfoDao = payInfoDao;
  }
  ```
  
  * 대역을 사용해 테스트 진행
  
  ```java
  public class PaySyncTest{
      // 대역 생성
  	private MemoryPayInfoDao memoryDao = new MemoryPayInfoDao();
      
      @Test
  	void someTest() throws IOException{
          PaySync paySync = new PaySync();
          paySync.setFilePath("src/test/reousrces/cp0111.csv");
          paySync.setPayInfoDao(memoryDao); // 대역으로 교체
          paySync.sync();
          
          // 대역을 이용한 결과 검증
          List<PayInfo> saveInfos = memoryDao.getAll();
          asseryEquals(2, savedInfos.size());
      }
  }
  ```

* 테스트 하고 싶은 코드 분리

* 시간이나 임의 값 생성 기능 분리
* 외부 라이브러리는 직접 사용하지 말고 감싸서 사용
  * 외부 라이브러리 감싼 클래스 생성
  * 감싼 클래스를 대역으로 불러와 사용