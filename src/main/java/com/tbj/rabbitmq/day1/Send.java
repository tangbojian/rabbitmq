package com.tbj.rabbitmq.day1;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/***
 * @author bjtang
 * @date   2017年9月6日  
 * @desc   rabbitmq helloworld
 */
public class Send {

	//队列名称
	private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) throws IOException {
		
		/***创建连接到rabbitMQ服务的工厂**/
		ConnectionFactory factory = new ConnectionFactory();
		/**设置RabbitMQ的ip,端口默认为15672**/
		factory.setHost("localhost");
		/**创建连接到rabbitMQ服务**/
		Connection connection = factory.newConnection();
		/**在tcp连接中创建一个信道**/
		Channel channel = connection.createChannel();
		/***在信道中指定一个消息队列**/
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		/**消息内容**/
		String message = "hello world";
		
		/**
	     * @param exchange the exchange to publish the message to
	     * @param routingKey the routing key
	     * @param props other properties for the message - routing headers etc
	     * @param body the message body
	     */
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println("[x] Send '" + message + "'");
		channel.close();
		connection.close();
	}
}
