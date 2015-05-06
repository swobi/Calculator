package calculator;

import java.text.DecimalFormat;
import java.util.Stack;

/**
 * @author Ming-Chien, Kao
 * The PostfixVisitor visits a set of CalculatorElement and prints out the
 * input string in postfix format. Spaces will be ignored.
 */
public class PostfixVisitor implements Visitor {

  private Stack<String> stack = new Stack<String>();
	
  public void visit(Operator operator) {
	//op represent the current operator or parenthesis being visited.
	String op = operator.getOperator();
	
	//If op is an operator, while there is an operator on the top of the stack,
	//and that operator is of greater or equal precedence than op, print/pop()
	//that operator. Finally, push(op).
	if (op.equals("+") || op.equals("-")) {
	  while (!stack.isEmpty() && !stack.peek().equals("(")) {
		System.out.print(stack.pop());
	  }
	  stack.push(op);
	} else if (op.equals("*") || op.equals("/")) {
	    while (!stack.isEmpty() && 
			  (getPriority(stack.peek()) >= getPriority(op))) {
		  System.out.print(stack.pop());
	  }
	  stack.push(op);
	} else if (op.equals("(")) {
		//If op is left parenthesis, push(op).
	    stack.push(op);
	} else if (op.equals(")")) {
		//If op is right parenthesis, print/pop() until a left parenthesis is
		//reached. Finally, pop() the left parenthesis(but not print it to output).
	    while (!stack.peek().equals("(")) {
		  System.out.print(stack.pop());
	    }
	    stack.pop();
	}
  }
  
  public void visit(Number number) {
	//if a number is visited, print it.
	DecimalFormat df = new DecimalFormat("###.######");
	System.out.print(df.format(number.getNumber()));
  }
  
  public void visit(Calculator calculator) {
	//when end of the expression is reached, finalize by popping and printing
	//what's left in the stack until empty.
	while(!stack.isEmpty()) {
	  System.out.print(stack.pop());
	}
	System.out.println();
  }
  
  /**
   * @param op
   * @return the priority(precedence) of the input operator
   */
  private int getPriority(String op)
  {
    if(op.equals("*") || op.equals("/")) {
      return 2;
    } else if(op.equals("+") || op.equals("-")) {
        return 1;
    } else {
        return Integer.MIN_VALUE;
    }
  }
  
}
