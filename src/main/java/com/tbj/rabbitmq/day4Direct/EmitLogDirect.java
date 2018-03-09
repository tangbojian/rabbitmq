package com.tbj.rabbitmq.day4Direct;

import java.util.Random;
import java.util.UUID;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogDirect {

	private static final String EXCHANGE_NAME = "ex_logs_direct";
	private static final String[] SEVERITIES = {"info", "warning", "error"};
	
	public static void main(String[] args) throws Exception {
		
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		//声明转发器和转发器类型
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		//共发送6条消息
		for(int i = 0; i < 6; i++){
			String serverity = getServerity();
			String message = serverity + "_log:" + UUID.randomUUID().toString();
			//发布消息到转发器
			channel.basicPublish(EXCHANGE_NAME, serverity, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'"); 
		}
		channel.close();
		connection.close();
	}

	/**
	 * 随机产生一种日志类型
	 * @return
	 */
	private static String getServerity() {
		Random random = new Random();
		int index = random.nextInt(3);
		return SEVERITIES[index];
	}
}
