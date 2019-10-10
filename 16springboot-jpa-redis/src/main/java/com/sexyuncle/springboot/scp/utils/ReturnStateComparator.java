package com.sexyuncle.springboot.scp.utils;

import com.sexyuncle.springboot.scp.enums.ReturnState;

public class ReturnStateComparator {

	public static int compareTo(ReturnState prev, ReturnState next) {
		if(prev == ReturnState.INTERNAL_AUDIT && next != ReturnState.INTERNAL_AUDIT) {
    		if(next == ReturnState.INIT) {
    			return 1;
    		}
    	}
    	if(prev != ReturnState.INTERNAL_AUDIT && next == ReturnState.INTERNAL_AUDIT) {
    		if(prev == ReturnState.INIT) {
    			return -1;
    		}
    		return 1;
    	}
    	
    	if(prev == ReturnState.INTERNAL_AUDIT && next == ReturnState.INTERNAL_AUDIT) {
    		return 0;
    	}
    	
    	return prev.getCode() - next.getCode();
	}

}
