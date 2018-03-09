package com.tbj.rabbitmq.day1;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/***
 * @author bjtang
 * @date   2017��9��6��  
 * @desc   rabbitmq helloworld
 */
public class Send {

	//��������
	private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) throws IOException {
		
		/***�������ӵ�rabbitMQ����Ĺ���**/
		ConnectionFactory factory = new ConnectionFactory();
		/**����RabbitMQ��ip,�˿�Ĭ��Ϊ15672**/
		factory.setHost("localhost");
		/**�������ӵ�rabbitMQ����**/
		Connection connection = factory.newConnection();
		/**��tcp�����д���һ���ŵ�**/
		Channel channel = connection.createChannel();
		/***���ŵ���ָ��һ����Ϣ����**/
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		/**��Ϣ����**/
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
