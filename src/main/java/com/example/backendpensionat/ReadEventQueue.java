package com.example.backendpensionat;

import com.example.backendpensionat.Models.RoomEventHappenings.*;
import com.example.backendpensionat.Models.RoomEvents;
import com.example.backendpensionat.Repos.RoomEventsRepo;
import com.example.backendpensionat.Services.RoomEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan
@RequiredArgsConstructor
public class ReadEventQueue implements CommandLineRunner {
    private final RoomEventsRepo roomEventsRepo;
    private final RoomEventService roomEventService;
    private String queueName = "74311a44-6c01-4e84-8ec7-6242d8a5058f";

    @Override
    public void run(String... args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("128.140.81.47");
        factory.setUsername("djk47589hjkew789489hjf894");
        factory.setPassword("sfdjkl54278frhj7");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            message = message.replace("RoomNo", "roomNo");
            message = message.replace("TimeStamp", "timeStamp");
            message = message.replace("CleaningByUser", "cleaningByUser");

            roomEventService.saveRoomEvent(message);
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });


    }

}
