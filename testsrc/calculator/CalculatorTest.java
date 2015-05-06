package calculator;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}
	
	@Test
	public void postfixTest1() {
	  String input = "3 + 4.44 * 2 / ( 1 - 5 )";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new PostfixVisitor());
	  assertTrue(outContent.toString().startsWith("34.442*15-/+"));
	}
	
	@Test
	public void postfixTest2() {
	  String input = "1 + 2 * 3 -4/5*6";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new PostfixVisitor());
	  assertTrue(outContent.toString().startsWith("123*+45/6*-"));
	}
	
	@Test
	public void postfixTest3() {
	  String input = "(1+2 * 3-4)/ (5*6)";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new PostfixVisitor());
	  assertTrue(outContent.toString().startsWith("123*+4-56*/"));
	}
	
	@Test
	public void postfixTest4() {
	  String input = "(999999999999*6)";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new PostfixVisitor());
	  assertTrue(outContent.toString().startsWith("9999999999996*"));
	}
	
	@Test
	public void prefixTest1() {
	  String input = "1/2*3-4+5/6/(7+8)";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new PrefixVisitor());
	  assertTrue(outContent.toString().startsWith("+-*/1234//56+78"));
	}
	
	@Test
	public void prefixTest2() {
	  String input = "((1+2) * 3 - (4-5))*(6+7)";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new PrefixVisitor());
	  assertTrue(outContent.toString().startsWith("*-*+123-45+67"));
	}
	
	@Test
	public void prefixTest3() {
	  String input = "(1+2+2-1+2-3+4		-5+2-1+5-4                   /6)";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new PrefixVisitor());
	  assertTrue(outContent.toString().startsWith("-+-+-+-+-++12212345215/46"));
	}
	
	@Test
	public void infixTest() {
	  String input = "3 + 4 * 2 / ( 1 - 5 )";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new InfixVisitor());
	  assertTrue(outContent.toString().startsWith("3+4*2/(1-5)"));
	}
	
	@Test
	public void evaluateTest1() {
	  String input = "3 + 4.34 * 2 / ( 1 - 5 )";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new EvaluateVisitor());
	  assertTrue(outContent.toString().startsWith("0.83"));
	}
	
	@Test
	public void evaluateTest2() {
	  String input = "((1*2) / 2 - (4/2))*(6+7)";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new EvaluateVisitor());
	  assertTrue(outContent.toString().startsWith("-13"));
	}
	
	@Test
	public void evaluateTest3() {
	  String input = "1/2*3-4+5/6/(7+8)";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new EvaluateVisitor());
	  assertTrue(outContent.toString().startsWith("-2.444444"));
	}
	
	@Test
	public void evaluateTest4() {
	  String input = "55555 + 44444 - 2222";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new EvaluateVisitor());
	  assertTrue(outContent.toString().startsWith("97777"));
	}
	
	@Test
	public void evaluateTest5() {
	  String input = "(5.12 * 6.23) - 7.34";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new EvaluateVisitor());
	  assertTrue(outContent.toString().startsWith("24.5576"));
	}
	
	@Test(expected=NumberFormatException.class)
	public void invalidCharacterTest1() {
	  String input = "1 + 2?";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new EvaluateVisitor());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void invalidCharacterTest2() {
	  String input = "1 + 2 ?";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new EvaluateVisitor());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void consecutiveOperatorTest() {
	  String input = "1 + 2 -*";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new EvaluateVisitor());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void consecutiveNumberTest() {
	  String input = "1 + 2 2";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new EvaluateVisitor());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void incorrectParenthesisTest1() {
	  String input = "(1 + 2) - 2)";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new EvaluateVisitor());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void incorrectParenthesisTest2() {
	  String input = "(1 + 2 * 2";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new EvaluateVisitor());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void incorrectParenthesisTest3() {
	  String input = "((((((((((((((((((((((((((";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new EvaluateVisitor());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void whiteSpaceStringTest() {
	  String input = "             ";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new EvaluateVisitor());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void emptyInputTest() {
	  String input = "";
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new PostfixVisitor());
	}
	
	@Test(expected=NullPointerException.class)
	public void nullInputTest() {
	  String input = null;
	  CalculatorElement calculator = new Calculator(input);
	  calculator.accept(new PostfixVisitor());
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	}

}
