package calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator implements CalculatorElement {
  
  private List<CalculatorElement> elements = new ArrayList<CalculatorElement>();
	
  public Calculator(String s) {
	inputParser(s);
  }
  
  public void accept(Visitor visitor) {
	for (CalculatorElement elem : elements) {
	  elem.accept(visitor);
	}
	visitor.visit(this);
  }
  
  private void inputParser(String input) {
	//Pattern to match digit, decimal point, or whitespace.
	String pattern = "[0-9]?(\\.)?(\\s+)?";
	String previousToken = "";
	int i = 0;
	int numParenthesis=0;
	
	//Invalid input checks for empty string and string with only white spaces
	if (input.length() == 0) {
	  throw new IllegalArgumentException("The input string is empty.");
	} else if (input.matches("(\\s+)+")) {
		throw new IllegalArgumentException("The input is all white spaces.");
	}
	
	//Read one character at a time, if it is an operator or parenthesis,
	//add it to the elements list as a new Operator. If it's a number,
	//read it entirely and add it to the elements list as a new Number.
	//Spaces are ignored. Also, invalid input conditions are checked. These
	//include the use of invalid characters, consecutive operators, 
	//consecutive numbers, and unequal number of left and right parenthesis.
	while (i < input.length()) {
	  String c = input.substring(i, i + 1);
	  if (!isOperator(c) && !isParenthesis(c) && !c.matches(pattern)) {
		throw new IllegalArgumentException("Invalid character used.");
	  }
	  if (isOperator(c)) {
		if (previousToken == "operator") {
			throw new IllegalArgumentException("Consecutive operators used.");
		}
		elements.add(new Operator(c));
		i++;
		previousToken = "operator";
	  } else if (c.equals("(")) {
		  elements.add(new Operator(c));
		  i++;
		  numParenthesis++;
		  previousToken = "parenthesis";
	  } else if (c.equals(")")) {
		  elements.add(new Operator(c));
		  i++;
		  numParenthesis--;
		  previousToken = "parenthesis";
	  } else if (c.equals(" ")) { 
		  i++;
		  continue;
	  } else {
		  if (previousToken == "number") {
			  throw new IllegalArgumentException("Consecutive numbers used.");
		  }
		  int start = i;
		  int end = i + 1;
		  if (end == input.length()) {
		    String stringValue = input.substring(start, end);
		    elements.add(new Number(stringValue));
		    i = end;
		    continue;
		  }
		  String newToken = input.substring(i + 1, i + 2);
		  while (!isOperator(newToken) && !isParenthesis(newToken) &&
				 !newToken.equals(" ") && i + 2 <= input.length()) {
			i++;
		    if (i + 2 <= input.length()) {
			  newToken = input.substring(i + 1, i + 2);
		    }
		    end = i + 1;
		  }

		  String stringValue = input.substring(start, end);
		  elements.add(new Number(stringValue));
		  i = end;
		  previousToken = "number";
	  }
	}
	if (numParenthesis != 0) {
	  throw new IllegalArgumentException("Incorrect use of left and" + 
			  " right parenthesis.");
	}
  }
  
  private boolean isOperator(String s) {
	if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
	  return true;
	}
	return false;
  }
  
  private boolean isParenthesis(String s) {
	if (s.equals("(") || s.equals(")")) {
	  return true;
	}
	return false;
  }
}
