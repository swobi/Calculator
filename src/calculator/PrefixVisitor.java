package calculator;

import java.text.DecimalFormat;
import java.util.Stack;

/**
 * @author Ming-Chien, Kao
 * The PrefixVisitor visits a set of CalculatorElement and prints out the
 * input string in prefix format. Spaces will be ignored.
 */
public class PrefixVisitor implements Visitor {
  //Two stacks are used, operatorStack to hold operator,
  //and operandStack to hold operand and intermediate results.
  private Stack<String> operatorStack = new Stack<String>();
  private Stack<String> operandStack = new Stack<String>();
  
  public void visit(Operator operator) {
	//op represent the current operator or parenthesis being visited.
	String op = operator.getOperator();
	
	//if op is left parenthesis, push it into operatorStack
	if (op.equals("(")) {
	  operatorStack.push(op);
	} else if (op.equals(")")) {
	    //if op is right parenthesis, do the following until a left
	    //parenthesis is reached and popped: pop o2 and o1 out of operandStack,
	    //then push(the top of operatorStack + o1 + o2) into operandStack.
	    while(!operatorStack.peek().equals("(")) {
		  String operand2 = operandStack.pop();
		  String operand1 = operandStack.pop();
		  operandStack.push(operatorStack.pop() + operand1 + operand2);
	    }
	    operatorStack.pop();
	} else if (op.equals("*") || op.equals("/")) {
	    //if op is * or /, and the top of the operatorStack has higher or
	    //equal precedence of op, do the following: pop o2 and o1 out of
	    //operandStack, then push(the top of the operatorStack + o1 + o2)
	    //into operandStack. Finally, push op into operatorStack.
	    while(!operatorStack.isEmpty() && 
		    (getPriority(operatorStack.peek()) >= getPriority(op))) {
		  String operand2 = operandStack.pop();
		  String operand1 = operandStack.pop();
		  operandStack.push(operatorStack.pop() + operand1 + operand2);
	    }
	    operatorStack.push(op);
	} else {
	    //if op is + or -, since all operator has at least equal precedence of op,
	    //unless top of the operatorStack is a left parenthesis, do the following:
	    //pop o2 and o1 out of operandStack, then push(the top of the operandStack
	    //+ o1 + o2) into operandStack. Finally, push op into operatorStack.
	    while(!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
		  String operand2 = operandStack.pop();
		  String operand1 = operandStack.pop();
		  operandStack.push(operatorStack.pop() + operand1 + operand2);
	    }
	    operatorStack.push(op);
	}
  }
  
  public void visit(Number number) {
	//if a number is visited, push it into the operandStack.
	DecimalFormat df = new DecimalFormat("###.######");
	operandStack.push(df.format(number.getNumber()));
  }
	  
  public void visit(Calculator calculator) {
	//when end of the expression is reached, do the following until the
	//operatorStack is empty: pop o2 and o1 out of operandStack, then
	//push(top of the operatorStack + o1 + o2) into the operandStack.
	//Finally, the result will be at the top of the operandStack.
	while(!operatorStack.isEmpty()) {
	  String operand2 = operandStack.pop();
	  String operand1 = operandStack.pop();
	  operandStack.push(operatorStack.pop() + operand1 + operand2);
	}
	System.out.println(operandStack.pop());
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
