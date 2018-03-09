package com.tbj.rabbitmq.day4Direct;

import java.util.Random;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogsDirect {

	private static final String EXCHANGE_NAME = "ex_logs_direct";
	private static final String[] SEVERITIES = { "info", "warning", "error" };

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		// 声明转发器和转发器类型
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		// 产生随机队列
		String queueName = channel.queueDeclare().getQueue();
		String serverity = getServerity();
		// 将队列和转发器绑定
		channel.queueBind(queueName, EXCHANGE_NAME, serverity);
		System.out.println(" [*] Waiting for " + serverity
				+ " logs. To exit press CTRL+C");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");
		}
	}

	/**
	 * 随机产生一种日志类型
	 * 
	 * @return
	 */
	private static String getServerity() {
		Random random = new Random();
		int index = random.nextInt(3);
		return SEVERITIES[index];
	}
}
