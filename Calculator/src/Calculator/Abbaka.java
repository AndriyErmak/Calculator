package Calculator;

import java.util.StringTokenizer;

public class Abbaka {
	
	private Expression expression;
	
	public Abbaka(Expression expresion){
		
		this.expression = expresion;
		expression = searchMistake(expression);
		
		// Create notation
		if(expression.getCarret()==null){
			//System.out.println("Correct expression");
			
			Formater formater = new Formater(expression);
			expression = formater.getResult();
			
			//print notation
			/*for(String str : formater.getResult().getNotation()){
				System.out.print(str);
			}
			System.out.println();*/
		}
	}
	
	private Expression searchMistake(Expression expression){
		String str_exp = expression.getExpression();  
		
		// Debug blank expression
		if(str_exp.length()==0){
			expression.setCarret(0);
			expression.setMistake("Expression is not defined!");
		}
		
		// Debug character is not valid
		if(expression.getCarret()==null){
			StringTokenizer st = new StringTokenizer(str_exp, " 0123456789+-()");
			if(st.countTokens()>0){
				expression.setCarret(str_exp.indexOf(st.nextToken()));
				expression.setMistake("Character is not valid!");
			}
		}
		
		// Debug operator in end expression without operand
		if(expression.getCarret()==null){
			String str_exr_trim = str_exp.trim(); 
			char end_char = str_exr_trim.charAt(str_exr_trim.length()-1);
			if("+-".indexOf(end_char)>-1){ //if end symbol is operand
				expression.setCarret(str_exp.lastIndexOf(end_char));
				expression.setMistake("End operator without operand!");
			}
		}
		
		// Debug pair bracket "()"
		if(expression.getCarret()==null){
			int car = 0;
			int open = 0;
			int close = 0;
			int length_exp = str_exp.length(); 
			
			while(car<length_exp){
				if(((int) str_exp.charAt(car))==40){ // ( open
					open++;
				}
				
				if(((int) str_exp.charAt(car))==41){ // ) close
					if(open>close){ 
						close++;
					}
					else{
						break;
					}
				}
				car++;
			}
			
			if(car==length_exp && (open-close)!=0){
				car = str_exp.lastIndexOf("("); // "(" is end expression
			}
			
			if(car<length_exp){
				expression.setCarret(car);
				switch(str_exp.charAt(car)){
					case 40: // ( open
						expression.setMistake("Open bracket without pair!");
						break;
					case 41: // ) close
						expression.setMistake("Close bracket without pair!");
						break;
				}
			}
		}
		
		return expression;
	}

	public Expression getResult() {
		return expression;
	}

}
