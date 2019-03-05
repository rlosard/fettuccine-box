package com.fettuccine.workers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Worker {

	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void receiveTasks() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		// factory.setHost("localhost");

		factory.setHost("bullfrog.rmq.cloudamqp.com");
		factory.setVirtualHost("ruidwryh");
		factory.setUsername("ruidwryh");
		factory.setPassword("rUI8fKIEMr20fLA-ECjmNhXnSR9RYXug");
		final Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();

		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		channel.basicQos(1);

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");

			System.out.println(" [x] Received from bullfrog.rmq.cloudamqp.com'" + message + "'");
			try {
				doWork(message);
			} finally {
				System.out.println(" [x] Done");
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			}
		};
		channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {
		});
		return;
	}

	private static void doWork(String task) {
		for (char ch : task.toCharArray()) {
			if (ch == '.') {
				try {
					Thread.sleep(1000);
					System.out.println("Worker send document to persistence");
				} catch (InterruptedException _ignored) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}
