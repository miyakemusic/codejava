package com.miyake.demo.shared;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.miyake.demo.entities.PassFailEnum;

public class PassFailCalculator {
	private ScriptEngine scriptEngine;

	public PassFailCalculator() {
		scriptEngine = new ScriptEngineManager().getEngineByName("graal.js");
	}
	
    public PassFailEnum judgePassFail(String criteria, String result) {
    	PassFailEnum passFail = PassFailEnum.Untested;
    	if (criteria != null && !criteria.isEmpty()) {
    		if (result != null && !result.isEmpty()) {
	    		
	    		try {
					Boolean ret = (Boolean)scriptEngine.eval(criteria.replace("v", result));
					passFail = ret ? PassFailEnum.Passed: PassFailEnum.Failed;
				} catch (ScriptException e) {
					e.printStackTrace();
				}	
    		}
    		else {
    			passFail = PassFailEnum.Untested;
    		}
    	}
    	else {
    		if (result != null && !result.isEmpty()) {
    			passFail = PassFailEnum.Tested;
    		}
    		else {
    			passFail = PassFailEnum.Untested;
    		}
    	}
    	
    	return passFail;
	}
}
