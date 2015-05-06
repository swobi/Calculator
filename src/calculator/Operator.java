package calculator;

public class Operator implements CalculatorElement {
  private String operator;
  
  public Operator(String op) {
	this.operator = op;
  }
  
  public String getOperator() {
	return operator;
  }
  
  public void accept(Visitor visitor) {
	visitor.visit(this);
  }
  
}
