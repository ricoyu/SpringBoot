package com.loserico.rocketmq.trans;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 在Producer发送事务消息后的回调函数, 只在发送端使用, 不能在消费端使用
 * <p>
 * Copyright: (C), 2019/12/24 17:47
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RocketMQTransactionListener(txProducerGroup = "myTxProducerGroup")
public class TransactionListener implements RocketMQLocalTransactionListener {
	
	private AtomicInteger transactionIndex = new AtomicInteger(0);
	
	private ConcurrentHashMap<String, Integer> localTransactions = new ConcurrentHashMap<>();
	
	@Override
	public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
		String transactionId = (String) msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
		System.out.printf("#### executeLocalTransaction is executed, msgTransactionId=%s %n", transactionId);
		
		int value = transactionIndex.getAndIncrement();
		int status = value % 3;
		localTransactions.put(transactionId, status);
		if (status == 0) {
			// 事务提交
			System.out.printf("    # COMMIT # Simulating msg %s related local transaction exec succeeded! ### %n", msg.getPayload());
			return RocketMQLocalTransactionState.COMMIT;
		}
		
		if (status == 1) {
			// 本地事务回滚
			System.out.printf("    # ROLLBACK # Simulating %s related local transaction exec failed! %n", msg.getPayload());
			return RocketMQLocalTransactionState.ROLLBACK;
		}
		
		// 事务状态不确定,待Broker发起 ASK 回查本地事务状态
		System.out.printf("    # UNKNOW # Simulating %s related local transaction exec UNKNOWN! \n");
		return RocketMQLocalTransactionState.UNKNOWN;
	}
	
	/**
	 * 在{@link TransactionListener#executeLocalTransaction(org.springframework.messaging.Message, java.lang.Object)}
	 * 中执行本地事务时可能失败, 或者异步提交, 导致事务状态暂时不能确定, broker在一定时间后 将会发起重试, broker会向producer-group发起ask回查, 这里producer->相当于server端,
	 * broker相当于client端, 所以由此可以看出broker&producer-group是 双向通信的。
	 *
	 * @param msg
	 * @return RocketMQLocalTransactionState
	 */
	@Override
	public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
		String transactionId = (String) msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
		RocketMQLocalTransactionState state = RocketMQLocalTransactionState.COMMIT;
		Integer status = localTransactions.get(transactionId);
		if (status != null) {
			switch (status) {
				case 0:
					state = RocketMQLocalTransactionState.UNKNOWN;
					break;
				case 1:
					state = RocketMQLocalTransactionState.COMMIT;
					break;
				case 2:
					state = RocketMQLocalTransactionState.ROLLBACK;
					break;
			}
		}
		
		System.out.printf("------ !!! checkLocalTransaction is executed once, msgTransactionId=%s, TransactionState=%s status=%s %n",
				transactionId, state, status);
		return state;
	}
}
