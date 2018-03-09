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
		// ����ת������ת��������
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		// �����������
		String queueName = channel.queueDeclare().getQueue();
		String serverity = getServerity();
		// �����к�ת������
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
	 * �������һ����־����
	 * 
	 * @return
	 */
	private static String getServerity() {
		Random random = new Random();
		int index = random.nextInt(3);
		return SEVERITIES[index];
	}
}
