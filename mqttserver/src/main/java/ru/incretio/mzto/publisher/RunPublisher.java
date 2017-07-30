package ru.incretio.mzto.publisher;

import ru.incretio.mzto.publisher.utils.Config;

import java.util.Scanner;

public class RunPublisher {
    public static void main(String[] args) {
        Publisher publisher = new Publisher(
                Config.getBrokerServer(),
                Config.getDirectoryUri(),
                Config.getTopicName());

        publisher.start();

        System.out.println("Server connected to " + Config.getBrokerServer());
        System.out.println("Server subscribe to " + Config.getTopicName());
        System.out.println("Server watch for " + Config.getDirectoryUri());

        System.out.println("Input something for exit");
        new Scanner(System.in).next();
        publisher.stop();
        System.out.println("Close server");
    }
}
