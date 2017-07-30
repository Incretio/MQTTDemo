package ru.incretio.mzto.consumer;

import ru.incretio.mzto.consumer.utils.Config;

import java.util.Scanner;

public class RunConsumer {
    public static void main(String[] args) {
        Consumer consumer =
                new Consumer(new SimpleMqttCallBack(),
                        Config.getBrokerServer(),
                        Config.getTopicName());

        consumer.start();

        System.out.println("Client connected to " + Config.getBrokerServer());
        System.out.println("Client subscribe to " + Config.getTopicName());

        System.out.println("Input something for exit");
        new Scanner(System.in).next();
        consumer.stop();
        System.out.println("Close client");
    }
}
