package calculator;

import java.text.DecimalFormat;
import java.util.Stack;

/**
 * @author Ming-Chien, Kao
 * The EvaluateVisitor visits a set of CalculatorElement, and prints the
 * evaluated value.
 */
public class EvaluateVisitor implements Visitor {
  //Two stacks are used, operatorStack to hold operators,
  //and valueStack to hold evaluation results.
  private Stack<String> operatorStack = new Stack<String>();
  private Stack<Double> valueStack = new Stack<Double>();
	
  public void visit(Operator operator) {
	//op represent the current operator or parenthesis being visited.
	String op = operator.getOperator();
	
	//if op is left parenthesis, push it into operatorStack
	if (op.equals("(")) {
	  operatorStack.push(op);
	} else if (op.equals(")")) {
		//if op is right parenthesis, do the following until a left
	    //parenthesis is reached and popped: pop o2 and o1 out of valueStack,
	    //and pop an operator o out of operatorStack. Then, push the evaluated 
		//result of o1 o o2 into valueStack. Finally, pop the left parenthesis.
		while(!operatorStack.peek().equals("(")) {
		  Double o2 = valueStack.pop();
		  Double o1 = valueStack.pop();
		  String o = operatorStack.pop();
		  if (o.equals("+")) {
		    valueStack.push(o1 + o2);
		  } else if (o.equals("-")) {
		      valueStack.push(o1 - o2);
		  } else if (o.equals("*")) {
		      valueStack.push(o1 * o2);
		  } else if (o.equals("/")) {
		      valueStack.push(o1 / o2);
		  }
		}
		operatorStack.pop();
	} else if (op.equals("*") || op.equals("/")) {
		//if op is * or /, and the top of the operatorStack has higher or
	    //equal precedence of op, do the following: pop o2 and o1 out of
	    //valueStack, and pop an operator o out of operatorStack. Then, push
		//the evaluated result of o1 o o2 into valueStack. Finally, push op 
		//into operatorStack.
		while(!operatorStack.isEmpty() && 
			(getPriority(operatorStack.peek()) >= getPriority(op))) {
		  Double o2 = valueStack.pop();
		  Double o1 = valueStack.pop();
		  String o = operatorStack.pop();
		  if (o.equals("*")) {
			valueStack.push(o1 * o2);
		  } else if (o.equals("/")) {
			  valueStack.push(o1 / o2);
		  }
		}
		operatorStack.push(op);
	} else if (op.equals("+") || op.equals("-")) {
		//if op is + or -, since all operator has at least equal precedence of op,
	    //unless top of the operatorStack is a left parenthesis, do the following:
	    //pop o2 and o1 out of valueStack, and pop an operator o out of 
		//operatorStack. Then, push the evaluated result of o1 o o2 into 
		//valueStack. Finally, push op into operatorStack.
		while(!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
		  Double o2 = valueStack.pop();
		  Double o1 = valueStack.pop();
		  String o = operatorStack.pop();
		  if (o.equals("+")) {
		    valueStack.push(o1 + o2);
		  } else if (o.equals("-")) {
		      valueStack.push(o1 - o2);
		  } else if (o.equals("*")) {
		      valueStack.push(o1 * o2);
		  } else if (o.equals("/")) {
		      valueStack.push(o1 / o2);
		  }
		}
		operatorStack.push(op);
	}
  }
  
  public void visit(Number number) {
	//if a number is visited, push it into the valueStack.
	valueStack.push(number.getNumber());
  }
  
  public void visit(Calculator calculator) {
	//when end of the expression is reached, do the following until the
	//operatorStack is empty: pop o2 and o1 out of valueStack, and pop an 
	//operator o out of operatorStack. Then, push the evaluated result of 
	//o1 o o2 into valueStack. Finally, when the operatorStack is empty,
	//the final result will be at the top of the valueStack.
	while(!operatorStack.isEmpty()) {
	  Double o2 = valueStack.pop();
	  Double o1 = valueStack.pop();
	  String o = operatorStack.pop();
	  if (o.equals("+")) {
	    valueStack.push(o1 + o2);
	  } else if (o.equals("-")) {
	      valueStack.push(o1 - o2);
	  } else if (o.equals("*")) {
	      valueStack.push(o1 * o2);
	  } else if (o.equals("/")) {
	      valueStack.push(o1 / o2);
	  }
	}
	DecimalFormat df = new DecimalFormat("###.######");
	System.out.println(df.format(valueStack.pop()));

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
