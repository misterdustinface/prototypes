package devan.input.conditionalInput;

import devan.input.button.VirtualButtonInput;

public class ConditionalInput implements Conditional {
	private ConditionalInputDef def;
	private VirtualButtonInput vKeys;
	
	public ConditionalInput(ConditionalInputDef def, VirtualButtonInput vKeys) {
		this.def = def;
		this.vKeys = vKeys;
	}

	@Override
	public boolean isConditionMet() {
		for(String key : def.vKeysUp) {
			if(vKeys.isPressed(key)) {
				return false;
			}
		}
		for(String key : def.vKeysDown) {
			if(!vKeys.isPressed(key)) {
				return false;
			}
		}
		for(Conditional condition : def.otherConditions) {
			if(!condition.isConditionMet()) {
				return false;
			}
		}
		return true;
	}
}
