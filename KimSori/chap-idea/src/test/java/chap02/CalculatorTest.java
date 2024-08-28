package chap02;

import org.example.chap02.Calculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    void plus() {
        int result = Calculator.plus(1, 2); // 작성할 클래스명과 메서드 이름 생각!
        assertEquals(3, result);
        assertEquals(5, Calculator.plus(4, 1));
    }
}
