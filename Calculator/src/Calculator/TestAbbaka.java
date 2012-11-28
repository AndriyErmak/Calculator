package Calculator;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Calculator.Abbaka;
import Calculator.Expression;

public class TestAbbaka {
	private Expression expression;
	
	@Before
	public void setUp() throws Exception {
		expression = new Expression();
	}

	@After
	public void tearDown() throws Exception {
		expression = null;
	}

	@Test
	public void expressionResultIsValid() {
		expression.setExpression("1+2-2");
		Abbaka abbaka = new Abbaka(expression);
		expression = abbaka.getResult();
		assertTrue(expression.getResult()==1L);  
	}
	
	@Test
	public void expressionIsEmpty() {
		expression.setExpression("");
		Abbaka abbaka = new Abbaka(expression);
		expression = abbaka.getResult();
		assertTrue(expression.getMistake().equals("Expression is not defined!"));  
	}
	
	@Test
	public void expressionIsBlank() {
		expression.setExpression(" ");
		Abbaka abbaka = new Abbaka(expression);
		expression = abbaka.getResult();
		assertTrue(expression.getMistake().equals("Expression is not defined!"));  
	}
	
	@Test
	public void characterIsNotValid() {
		expression.setExpression("1*2");
		Abbaka abbaka = new Abbaka(expression);
		expression = abbaka.getResult();
		assertTrue(expression.getMistake().equals("Character is not valid!"));  
	}
	
	@Test
	public void endOperatorWithoutOperand() {
		expression.setExpression("1+2-");
		Abbaka abbaka = new Abbaka(expression);
		expression = abbaka.getResult();
		assertTrue(expression.getMistake().equals("End operator without operand!"));  
	}
	
	@Test
	public void closeBracketWithoutPair() {
		expression.setExpression("(1+2))-2");
		Abbaka abbaka = new Abbaka(expression);
		expression = abbaka.getResult();
		assertTrue(expression.getMistake().equals("Close bracket without pair!"));  
	}
	
	@Test
	public void openBracketWithoutPair() {
		expression.setExpression("(1+(2-2)");
		Abbaka abbaka = new Abbaka(expression);
		expression = abbaka.getResult();
		//assertTrue(expression.getCarret()>=0);
		assertTrue(expression.getMistake().equals("Open bracket without pair!"));  
	}
	
	@Test
	public void operatorWithoutOperandInBracket() {
		expression.setExpression("1+2-(-)");
		Abbaka abbaka = new Abbaka(expression);
		expression = abbaka.getResult();
		assertTrue(expression.getMistake().equals("Operator without operand in bracket!"));  
	}
	
	@Test
	public void bracketWithoutExpression() {
		//expression.setExpression("(1+2)-()");
		expression.setExpression("(1+2+())-3");
		Abbaka abbaka = new Abbaka(expression);
		expression = abbaka.getResult();
		assertTrue(expression.getMistake().equals("Pair bracket without expression!"));  
	}
	
	@Test
	public void twoOperatorWithoutOperand() {
		expression.setExpression("1+2+ + 2-1");
		Abbaka abbaka = new Abbaka(expression);
		expression = abbaka.getResult();
		assertTrue(expression.getMistake().equals("Two operators without operand!"));  
	}
	
	@Test
	public void expressionWithoutOperator() {
		expression.setExpression("-(1+2) 2");
		Abbaka abbaka = new Abbaka(expression);
		expression = abbaka.getResult();
		assertTrue(expression.getMistake().equals("Operand without operator!"));  
	}
	
	@Test
	public void operandIsNotValid() {
		expression.setExpression("(1+2)+2 3");
		Abbaka abbaka = new Abbaka(expression);
		expression = abbaka.getResult();
		assertTrue(expression.getMistake().equals("Operand is not correct!"));  
	}
	
	@Test
	public void dotOperandIsNotValid() {
		expression.setExpression("(1+2)+2.3");
		Abbaka abbaka = new Abbaka(expression);
		expression = abbaka.getResult();
		assertTrue(expression.getMistake().equals("Character is not valid!"));  
	}
}
