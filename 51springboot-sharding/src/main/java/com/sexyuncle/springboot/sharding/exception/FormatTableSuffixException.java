package com.sexyuncle.springboot.sharding.exception;

import com.sexyuncle.springboot.sharding.enumeration.RoutingErrors;

/**
 * <p>
 * Copyright: (C), 2020/2/14 18:37
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class FormatTableSuffixException extends RoutingException {
	
	public FormatTableSuffixException() {
		super(RoutingErrors.FORMAT_TABLE_SUFFIX_ERROR.getMsg());
		setErrorCode(RoutingErrors.FORMAT_TABLE_SUFFIX_ERROR.getCode());
		setErrorMsg(RoutingErrors.FORMAT_TABLE_SUFFIX_ERROR.getMsg());
	}
	
}
