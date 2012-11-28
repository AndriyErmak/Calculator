package Calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Formater {
	
	private final String OPERATORS = "+-";
	private final String BRACKETS = "()";
	
	private StringTokenizer stExpression;
	private List<String> lstNotation;
	private Stack<String> stkOperators;
	
	private Expression expression;
	private int operator_priority;
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
		expression = createNotationFromExpression(expression);
		
		// Expression calculate
		if(expression.getCarret()==null){
			Arithmometer arithmometer = new Arithmometer(expression);
			expression = arithmometer.getResult();
		}
	}
	
	private Expression createNotationFromExpression(Expression expression){
		
		stExpression = new StringTokenizer(this.expression.getExpression(), OPERATORS+BRACKETS, true);
		String token;
		while(stExpression.hasMoreTokens() && expression.getCarret()==null){
			token = stExpression.nextToken().trim();
			if(token.length()>0){
				analyzeToken(token);
			}
		}
		
		while(!stkOperators.empty()){ // all operator from stack to Notation 
			 lstNotation.add(stkOperators.pop());
		}
		expression.setNotation(lstNotation);
		
		return expression;
	}
	
	private void analyzeToken(String token){
		if(OPERATORS.indexOf(token)>=0 || BRACKETS.indexOf(token)>=0){ // if operator or "()"
			if("(".equals(token)) // if "("  - in stack
				openBracket(token);
			if(")".equals(token)) // if ")"
				closeBracket();
			if(OPERATORS.indexOf(token)>=0) // if operator
				operator(token);
		}
		else{ // if operand
			operand(token);
		}
	}
	
	
	private void openBracket(String open_bracket){
		if(!start_expression && !operator_rotation){ // Debug "(" without operator ("123(")
			carretOpenBracket();
			expression.setMistake("Open bracket without operator!");
		} else {
			operator_rotation = false;
			start_expression = true;
			end_expression = false;
			stkOperators.push(open_bracket);
		}
	}
	
	private void closeBracket(){
		if (operator_rotation) { // Debug operator without operand in bracket
			carretCloseBracket();
			expression.setMistake("Operator without operand in bracket!");
		} else {
			if (start_expression) { // Debug empty "()"
				carretCloseBracket();
				expression.setMistake("Pair bracket without expression!");
			} else {
				operator_rotation = false;
				start_expression = false;
				end_expression = true;
				String buffer;
				do { // operator from stack to Notation, if it is not "("
					buffer = stkOperators.pop();
					if(!"(".equals(buffer)) lstNotation.add(buffer); 
				} while (!"(".equals(buffer));
			}
		}
	}
	
	private void operator(String operator){
		if (operator_rotation) { // Debug operator without operand in bracket
			String find = "";
			while(stExpression.hasMoreTokens()){
				find += stExpression.nextToken();
			}
			expression.setCarret(expression.getExpression().lastIndexOf(find)-2);
			expression.setLength(2);
			expression.setMistake("Two operators without operand!");
		} else {
			operator_rotation = true;
			start_expression = false;
			end_expression = false;
			operator_priority = this.getPriority(operator);
			while(!stkOperators.empty() && (this.getPriority(stkOperators.peek()) >= operator_priority)){
				//if operator priority in stack >= priority new operator
				lstNotation.add(stkOperators.pop()); // operator from stack to Notation
			}
			if(stkOperators.empty() || (this.getPriority(stkOperators.peek()) < operator_priority)){ 
				//if operator priority in stack < priority new operator
				stkOperators.push(operator);
			}
		}
	}
	
	private void operand(String operand){
		if (end_expression) { 	// Debug (1+2)3
			String find = "";
			int length = operand.length()+1;
			while(stExpression.hasMoreTokens()){
				find += stExpression.nextToken();
			}
			
			expression.setCarret(expression.getExpression().lastIndexOf(find)-length);
			expression.setLength(length);
			expression.setMistake("Operand without operator!");
		} else {
			operator_rotation = false;
			start_expression = false;
			end_expression = false;
			lstNotation.add(operand);
		}
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
	
	private int getPriority(String operator){
		int priority = 0;
		
		if(BRACKETS.indexOf(operator)>=0)
			priority = 1;
		if("+-".indexOf(operator)>=0)
			priority = 2;

		return priority;
	}

	public Expression getResult() {
		return expression;
	}

}
