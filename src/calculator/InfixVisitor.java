package calculator;

import java.text.DecimalFormat;

/**
 * @author Ming-Chien, Kao
 * The InfixVisitor visits a set of CalculatorElement and prints out the
 * input string in infix format. Spaces will be ignored.
 */
public class InfixVisitor implements Visitor {

  public void visit(Operator operator) {
	System.out.print(operator.getOperator());
  }
  
  public void visit(Number number) {
	DecimalFormat df = new DecimalFormat("###.#######");
	System.out.print(df.format(number.getNumber()));
  }
  
  public void visit(Calculator calculator) {
	System.out.println();
  }
}
