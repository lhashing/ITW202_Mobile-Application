package edu.gcit.todo_10;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
@SmallTest
public class ExampleUnitTest {
    private Calculator cal;

//    @Before
//    public void setUp(){
//        cal = new Calculator();
//    }
//
//    @Test
//    public void addTwoNumber(){
//        double result = cal.add(1d, 2d);
//        assertThat(result, is(equalTo(3d)));
//    }
//
//    @Test
//    public void addTwoNumberNegative(){
//        double result = cal.add(-1d, 2d);
//        assertThat(result, is(equalTo(1d)));
//    }
    /**
     * Set up the environment for testing
     */
    @Before
    public void setUp() {
        cal = new Calculator();
    }

    /**
     * Test for simple addition
     */
    @Test
    public void addTwoNumbers() {
        double resultAdd = cal.add(1d, 1d);
        assertThat(resultAdd, is(equalTo(2d)));
    }
    /**
     * Test for addition with a negative operand
     */
    @Test
    public void addTwoNumbersNegative() {
        double resultAdd = cal.add(-1d, 2d);
        assertThat(resultAdd, is(equalTo(1d)));
    }
    /**
     * Test for addition where the result is negative
     */
    @Test
    public void addTwoNumbersWorksWithNegativeResult() {
        double resultAdd = cal.add(-1d, -17d);
        assertThat(resultAdd, is(equalTo(-18d)));
    }
    /**
     * Test for floating-point addition
     */
    @Test
    public void addTwoNumbersFloats() {
        double resultAdd = cal.add(1.111d, 1.111d);
        assertThat(resultAdd, is(equalTo(2.222)));
    }

    /**
     * Test for especially large numbers
     */
    @Test
    public void addTwoNumbersBignums() {
        double resultAdd = cal.add(123456781d, 111111111d);
        assertThat(resultAdd, is(equalTo(234567892d)));
    }
    /**
     * Test for simple subtraction
     */
    @Test
    public void subTwoNumbers() {
        double resultSub = cal.sub(1d, 1d);
        assertThat(resultSub, is(equalTo(0d)));
    }

    /**
     * Test for simple subtraction with a negative result
     */
    @Test
    public void subWorksWithNegativeResult() {
        double resultSub = cal.sub(1d, 17d);
        assertThat(resultSub, is(equalTo(-16d)));
    }

    /**
     * Test for simple division
     */
    @Test
    public void divTwoNumbers() {
        double resultDiv = cal.div(32d,2d);
        assertThat(resultDiv, is(equalTo(16d)));
    }

    /**
     * Test for divide by zero; must throw IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void divDivideByZeroThrows() {
        cal.div(32d,0d);
    }

    /**
     * Test for simple multiplication
     */
    @Test
    public void mulTwoNumbers() {
        double resultMul = cal.mul(32d, 2d);
        assertThat(resultMul, is(equalTo(64d)));
    }

}
