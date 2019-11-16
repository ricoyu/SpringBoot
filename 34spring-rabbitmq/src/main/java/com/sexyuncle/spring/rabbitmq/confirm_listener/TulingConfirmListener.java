package com.sexyuncle.spring.rabbitmq.confirm_listener;

import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;

/**
 * <p>
 * Copyright: (C), 2019/11/4 20:43
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class TulingConfirmListener implements ConfirmListener {
	
	/**
	 * @param deliveryTag 唯一消息Id
	 * @param multiple    是否批量
	 * @throws IOException
	 */
	@Override
	public void handleAck(long deliveryTag, boolean multiple) throws IOException {
		System.out.println("当前时间:" + System.currentTimeMillis() + "TulingConfirmListener handleAck:" + deliveryTag);
	}
	
	/**
	 * 处理异常
	 *
	 * @param deliveryTag
	 * @param multiple
	 * @throws IOException
	 */
	@Override
	public void handleNack(long deliveryTag, boolean multiple) throws IOException {
		System.out.println("TulingConfirmListener handleNack:"+deliveryTag);
	}
}
