package Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Calculator {

	public static void main(String[] args) {
		Expression expression;
		String str_exp = "";
		BufferedReader buffer_reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("CALCULATOR");
		System.out.println("Enter 'quit' for exit.");
		
		do {
			System.out.println("Enter an expression for the calculation:");
			try {
				str_exp = buffer_reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(!str_exp.equals("quit")){
				
				expression = new Expression(str_exp);;
				 
				// Expression check and calculate 
				Abbaka abbaka = new Abbaka(expression);
				expression = abbaka.getResult();
				
				// Return result
				if(expression.getCarret()==null){
					System.out.println("Result of calculation expression: " + expression.getResult());
					System.out.println();
				}
				else{
					System.out.println(expression.getCarret() + " " + expression.getMistake());
					System.out.println();
				}
			}
		} while (!str_exp.equals("quit"));
	}
}
