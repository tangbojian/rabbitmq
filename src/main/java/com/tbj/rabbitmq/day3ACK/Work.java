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
		//����ack(����Ӧ�����)
		boolean ack = false;
		channel.basicConsume(QUEUE_NAME, ack, consumer);

		while (true) {
			Delivery nextDelivery = consumer.nextDelivery();
			String message = new String(nextDelivery.getBody());
			System.out.println(hashCode + " [x] Received '" + message + "'");
			doWork(message);
			System.out.println(hashCode + " [x] Done");
			//�ڴ�����ҵ���߼�֮��,����Ӧ�ø�RabbitMQ, 
			// ���͵�Ϊ����Ϣ��tag 
			// false ��ʾ������ true Ϊ��������һ����ack����С�ڸ�tag����Ϣ
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
