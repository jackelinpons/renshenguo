package com.antsdouble.rpcs;

import com.antsdouble.simpledemo.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class RPCServer {
    private static final String RPC_QUEUE_NAME = "rpc_queue";

    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                AMQP.BasicProperties properties1 = new AMQP.BasicProperties.Builder().correlationId(properties.getCorrelationId()).build();
                String mes = new String(body, "UTF-8");
                int num = Integer.valueOf(mes);
                System.out.println("接收数据:" + num);
                num = fib(num);
                channel.basicPublish("", properties.getReplyTo(), properties1, String.valueOf(num).getBytes());
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(RPC_QUEUE_NAME,false,consumer);
        while (true){
            synchronized (consumer){
                try {
                    consumer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static int fib(int n) {
        System.out.println(n);
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }
}
