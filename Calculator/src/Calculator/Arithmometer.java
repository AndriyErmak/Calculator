package Calculator;

import java.util.List;
import java.util.Stack;

public class Arithmometer {
	
	private final String OPERATORS = "+-";
	private List<String> lstNotation;
	private Stack<Long> stkOperands;
	private Long left_operand; 
	private Long right_operand;
	private Long buffer;
	private Expression result;
	
	public Arithmometer(Expression expression){
		this.result = expression;
		lstNotation = expression.getNotation();
		stkOperands = new Stack<Long>();
		
		for(String oper : lstNotation){
			//System.out.println("In stack " + oper + "  " + OPERATORS.indexOf(oper));
			if(OPERATORS.indexOf(oper)>=0){
				right_operand = stkOperands.pop();
				if(!stkOperands.empty()){
					left_operand = stkOperands.pop();
				}
				else{ //if unary minus or plus
					left_operand = new Long(0);
				}
				this.doCalculate(oper);
			}
			else{
				try{
					buffer = new Long(oper);
				}
				catch (NumberFormatException e) { // if "1 2" (blank in operand)
					expression.setCarret(expression.getExpression().indexOf(oper));
					expression.setLength(oper.length());
					expression.setMistake("Operand is not correct");
					break;
				}
				stkOperands.add(buffer);
			}
		}
		
		if(expression.getCarret()==null) expression.setResult(stkOperands.pop());
	}

	private void doCalculate (String operator){
		if(operator.equals("+")){
			stkOperands.add(left_operand + right_operand); 
		}
		if(operator.equals("-")){
			stkOperands.add(left_operand - right_operand);
		}
	}
	
	public Expression getResult() {
		return result;
	}
}
