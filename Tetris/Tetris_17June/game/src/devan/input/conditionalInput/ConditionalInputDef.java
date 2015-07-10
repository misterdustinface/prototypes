package devan.input.conditionalInput;

import java.util.LinkedHashSet;

public class ConditionalInputDef {
	public LinkedHashSet<String> vKeysUp;
	public LinkedHashSet<String> vKeysDown;
	public LinkedHashSet<Conditional> otherConditions;
	
	public ConditionalInputDef() {
		vKeysUp = new LinkedHashSet<String>();
		vKeysDown = new LinkedHashSet<String>();
		otherConditions = new LinkedHashSet<Conditional>();
	}
}
