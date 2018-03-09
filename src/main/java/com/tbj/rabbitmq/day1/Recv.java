package com.tbj.rabbitmq.day1;

import java.io.IOException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

public class Recv {
	//��������
	private final static String QUEUE_NAME = "hello";
	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		//�����Ӻʹ���Ƶ�����뷢�Ͷ�һ��  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("localhost");  
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
        //�������У���ҪΪ�˷�ֹ��Ϣ�����������д˳��򣬶��л�������ʱ�������С�  
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);  
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        /***
         * �����������Ѷ���pull���ݻ���push����
         */
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);
        while(true){
        	Delivery nextDelivery = consumer.nextDelivery();
        	String message = new String(nextDelivery.getBody());
        	System.out.println("[x] Receivedd '" + message + "'");
        }
	}
}
