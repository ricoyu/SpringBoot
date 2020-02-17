package com.sexyuncle.springboot.sharding.exception;

import com.sexyuncle.springboot.sharding.enumeration.RoutingErrors;
import lombok.Data;

/**
 * 加载路由策略和配置配置文件不匹配 
 * <p>
 * Copyright: Copyright (c) 2020-02-14 18:45
 * <p>
 * Company: Sexy Uncle Inc.
 * <p>
 
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
@Data
public class RoutingStategyUnmatchException extends RoutingException {

    public RoutingStategyUnmatchException() {
        super(RoutingErrors.LOADING_STATEGY_UN_MATCH.getMsg());
        setErrorCode(RoutingErrors.LOADING_STATEGY_UN_MATCH.getCode());
        setErrorMsg(RoutingErrors.LOADING_STATEGY_UN_MATCH.getMsg());

    }
}