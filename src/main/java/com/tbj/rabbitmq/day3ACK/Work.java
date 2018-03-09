package com.tbj.rabbitmq.day3ACK;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Work {

	private final static String QUEUE_NAME = "workqueueACK";

	public static void main(String[] args) throws Exception {

		int hashCode = Work.class.hashCode();

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connecttion = factory.newConnection();
		Channel channel = connecttion.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(hashCode
				+ " [*] Waiting for messages. To exit press CTRL+C");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		//开启ack(开启应答机制)
		boolean ack = false;
		channel.basicConsume(QUEUE_NAME, ack, consumer);

		while (true) {
			Delivery nextDelivery = consumer.nextDelivery();
			String message = new String(nextDelivery.getBody());
			System.out.println(hashCode + " [x] Received '" + message + "'");
			doWork(message);
			System.out.println(hashCode + " [x] Done");
			//在处理完业务逻辑之后,发送应该给RabbitMQ, 
			// 回送的为该消息的tag 
			// false 表示不批量 true 为批量：将一次性ack所有小于该tag的消息
			channel.basicAck(nextDelivery.getEnvelope().getDeliveryTag(), false);
		}
	}

	private static void doWork(String task) throws InterruptedException {
		for (char ch : task.toCharArray()) {
			if (ch == '.')
				Thread.sleep(1000);
		}
	}

}
