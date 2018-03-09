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
	//队列名称
	private final static String QUEUE_NAME = "hello";
	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		//打开连接和创建频道，与发送端一样  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("localhost");  
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
        //声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。  
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);  
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        /***
         * 用来申明消费断是pull数据还是push数据
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
