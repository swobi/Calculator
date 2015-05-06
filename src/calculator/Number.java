package calculator;

public class Number implements CalculatorElement {
  private double number;
  
  public Number(String s) {
	double d = Double.parseDouble(s);
	this.number = d;
  }
  
  public double getNumber() {
	return number;
  }
  
  public void accept(Visitor visitor) {
	visitor.visit(this);
  }
  
}
