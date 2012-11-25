package Calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Formater {
	
	private final String OPERATORS = "+-()";
	
	private StringTokenizer stExpression;
	private List<String> lstNotation;
	private Stack<String> stkOperators;
	private String buffer;
	private Expression expression;
	private int buffer_priority;
	private boolean operator_rotation; 
	private boolean start_expression;
	private boolean end_expression;
	
	public Formater (Expression expression){
		this.expression = expression; 
		lstNotation = new ArrayList<String>();
		stkOperators = new Stack<String>();
		operator_rotation = false; //if current symbol is operator operator_rotation set true;
		start_expression = true; 
		end_expression = false; // if current symbol is ")" to true
		expression = createNotation(expression);
		
		// Expression calculate
		if(expression.getCarret()==null){
			Arithmometer arithmometer = new Arithmometer(expression);
			expression = arithmometer.getResult();
		}
	}
	
	private Expression createNotation(Expression expression){
		
		stExpression = new StringTokenizer(this.expression.getExpression(), OPERATORS, true);
		
		while(stExpression.hasMoreTokens()){
			buffer = stExpression.nextToken().trim();
			if(buffer.length()>0){
				if(OPERATORS.indexOf(buffer)>=0){ // if operator or "()"
					if("(".equals(buffer)){ // if "("  - in stack
						if(!start_expression && !operator_rotation){ // Debug "(" without operator ("123(")
							carretOpenBracket();
							expression.setMistake("Open bracket without operator!");
							break;
						} else {
							operator_rotation = false;
							start_expression = true;
							end_expression = false;
							stkOperators.push(buffer);
						}
					}
					
					if(")".equals(buffer)){ // if ")"
						if (operator_rotation) { // Debug operator without operand in bracket
							carretCloseBracket();
							expression.setMistake("Operator without operand in bracket!");
							break;
						} else {
							if (start_expression) { // Debug empty "()"
								carretCloseBracket();
								expression.setMistake("Pair bracket without expression!");
								break;
							} else {
								operator_rotation = false;
								start_expression = false;
								end_expression = true;
								do { // operator from stack to Notation, if it is not "("
									buffer = stkOperators.pop();
									if(!"(".equals(buffer)) lstNotation.add(buffer); 
								} while (!"(".equals(buffer));
							}
						}
					}
					
					if("()".indexOf(buffer)<0){ // if operator
						if (operator_rotation) { // Debug operator without operand in bracket
							String find = "";
							while(stExpression.hasMoreTokens()){
								find += stExpression.nextToken();
							}
							expression.setCarret(expression.getExpression().lastIndexOf(find)-2);
							expression.setLength(2);
							expression.setMistake("Two operators without operand!");
							break;
						} else {
							operator_rotation = true;
							start_expression = false;
							end_expression = false;
							buffer_priority = this.getPriority(buffer.charAt(0));
							while(!stkOperators.empty() && (this.getPriority(stkOperators.peek().charAt(0)) >= buffer_priority)){
								//if operator priority in stack >= priority new operator
								lstNotation.add(stkOperators.pop()); // operator from stack to Notation
							}
							if(stkOperators.empty() || (this.getPriority(stkOperators.peek().charAt(0)) < buffer_priority)){ 
								//if operator priority in stack < priority new operator
								stkOperators.push(buffer);
							}
						}
					}
				}
				else{ // if operand
					if (end_expression) { 					// Debug (1+2)3
						String find = "";
						int length = buffer.length()+1;
						while(stExpression.hasMoreTokens()){
							find += stExpression.nextToken();
						}
						
						expression.setCarret(expression.getExpression().lastIndexOf(find)-length);
						expression.setLength(length);
						expression.setMistake("Operand without operator!");
						break;
					} else {
						operator_rotation = false;
						start_expression = false;
						end_expression = false;
						lstNotation.add(buffer);
					}
				}
			}
		}
		
		while(!stkOperators.empty()){ // all operator from stack to Notation 
			 lstNotation.add(stkOperators.pop());
		}
		expression.setNotation(lstNotation);
		
		return expression;
	}
	
	private void carretCloseBracket(){
		String find = "";
		while(stExpression.hasMoreTokens()){
			find += stExpression.nextToken();
		}
		if(find.length()==0) find = ")"; // if mistake in end expression
		expression.setCarret(expression.getExpression().lastIndexOf(find)-2);
		expression.setLength(2);
	}
	
	private void carretOpenBracket(){
		String find = "";
		while(stExpression.hasMoreTokens()){
			find += stExpression.nextToken();
		}
		expression.setCarret(expression.getExpression().lastIndexOf(find)-2);
		expression.setLength(2);
	}
	
	private int getPriority(char operator){
		int priority = 0;
		switch(operator){
			case 40: // (
			case 41: // )
				priority = 1;
				break;
			case 43: // +
			case 45: // -
				priority = 2;
				break;
		}
		return priority;
	}

	public Expression getResult() {
		return expression;
	}

}
