# TDD 4주차 - chap7, 8

## Chapter 7. 대역

### 대역의 필요성
테스트 대상이 외부 요인에 의존 시, 작성이 어려워지고 결과도 예측할 수 없게됨

의존 요인 때문에 테스트가 어려울 경우 **대역**을 사용해 테스트 진행

> _Double_ 더블
> 
> test double. 테스트에서 진짜 대신 사용할 대역 <br/> ex) 스텁, 가짜, 스파이

### 대역의 종류

| 종류        | 설명                                 |
|-----------|------------------------------------|
| 스텁 _Stub_ | 구현을 단순한 것으로 대체. 단순히 원하는 동작 수행      |
| 가짜 _Fake_ | 실제 동작하는 구현 제공                      |
| 스파이 _Spy_ | 호출된 내역 기록. 테스트 결과 검증 시 사용. 스텁의 일종  |
| 모의 _Mock_ | 기대한 대로 상호작용 하는지 행위 검증. 스텁이자 스파이도 됨 |

#### Mockito
모의 객체 생성, 검증, 스텁을 지원하는 프레임워크


### 대역과 개발속도
대역은 의존하는 대상을 구현하지 않아도 테스트 대상을 완성할 수 있게함
대기시간을 줄여 개발속도 향상에 기여


### 모의 객체 남용 자제
모의 객체를 과하게 사용하면 오히려 테스트코드가 복잡해짐

모의 객체의 메서드 호출여부를 결과 검증 수단으로 사용하는 것 주의




## Chapter 8. 테스트 가능한 설계

### 테스트 어려운 코드
- 하드코딩된 경로
- 의존객체 직접 생성
- 정적 메서드 사용
- 실행 시점 따라 달라지는 결과
- 역할이 섞인 코드
- 콘솔에서 입력받거나 출력
- 메서드 중간에 소켓 통신 포함
- 의존대상이 _final_ 일 때
- 테스트 대상 소스를 소유하지 않아 수정이 어려움


### 테스트 가능한 설계
- 하드코딩된 상수를 생성자나 메서드 파라미터로 받기
- 의존대상 주입받기
  - 대역을 사용할 수도 있음
- 테스트할 코드 분리
- 시간이나 임의 값 생성 기능 분리
  - 별도로 분리한 값을 사용해 대역으로 처리할 수 있어야함
- 외부 라이브러리를 감싸서 사용하기
  - 직접 사용하지 않고, 외부 라이브러리와 연동하기 위한 타입 새로 생성
  - 의존 대상이 _final_ 인 경우에도 사용 가능한 방법
