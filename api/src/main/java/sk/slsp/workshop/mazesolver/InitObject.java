package sk.slsp.workshop.mazesolver;

import java.io.Serializable;

public class InitObject implements Serializable {

	private String someVariable;

	public InitObject() {
		this.someVariable = "";
	}

	public InitObject(String someVariable) {
		this.setSomeVariable(someVariable);
	}

	public void setSomeVariable(String someVariable) {
		this.someVariable = someVariable;
	}

	public String getSomeVariable() {
		return this.someVariable;
	}

}
