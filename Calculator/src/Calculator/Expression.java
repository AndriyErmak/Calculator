package Calculator;

import java.util.List;

public class Expression {
	
	
	private String expression;
	private long result;
	private Integer carret;
	private Integer length;
	private String mistake; 
	private List<String> notation;
	
	public  Expression(){}
	
	public Expression(String expression) {
		super();
		this.expression = expression;
	}

	
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	public long getResult() {
		return result;
	}

	public void setResult(long result) {
		this.result = result;
	}
	
	public Integer getCarret() {
		return carret;
	}

	public void setCarret(Integer carret) {
		this.carret = carret;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getMistake() {
		return mistake;
	}

	public void setMistake(String mistake) {
		this.mistake = mistake;
	}

	public List<String> getNotation() {
		return notation;
	}

	public void setNotation(List<String> notation) {
		this.notation = notation;
	}

	
	
}
