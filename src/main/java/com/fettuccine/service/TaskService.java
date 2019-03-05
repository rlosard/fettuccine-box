package com.fettuccine.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class TaskService {

	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void sendTask(String body) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();

		// RabbitMQ host
		//factory.setHost("localhost");
		
	      factory.setHost("bullfrog.rmq.cloudamqp.com");
	      factory.setVirtualHost("ruidwryh");
	      factory.setUsername("ruidwryh");
	      factory.setPassword("rUI8fKIEMr20fLA-ECjmNhXnSR9RYXug");

		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

			String message = String.join(" ", body);

			channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
					message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + message + "'");
		}
	}

}
