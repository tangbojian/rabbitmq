package com.tbj.rabbitmq.day2;

import java.io.IOException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class NewTask {
	
	private final static String QUEUE_NAME = "workqueue";
	
	public static void main(String[] args) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		for(int i = 0; i < 10; i++){
			String dots = "";
			for(int j = 0; j <= i; j++){
				dots += ".";
			}
			String message = "helloworld" + dots + dots.length();
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println("[x] Send '" + message + "'");
		}
		channel.close();
		connection.close();
	}
}
