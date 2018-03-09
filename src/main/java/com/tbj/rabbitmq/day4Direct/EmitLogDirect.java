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
		//����ת������ת��������
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		//������6����Ϣ
		for(int i = 0; i < 6; i++){
			String serverity = getServerity();
			String message = serverity + "_log:" + UUID.randomUUID().toString();
			//������Ϣ��ת����
			channel.basicPublish(EXCHANGE_NAME, serverity, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'"); 
		}
		channel.close();
		connection.close();
	}

	/**
	 * �������һ����־����
	 * @return
	 */
	private static String getServerity() {
		Random random = new Random();
		int index = random.nextInt(3);
		return SEVERITIES[index];
	}
}
