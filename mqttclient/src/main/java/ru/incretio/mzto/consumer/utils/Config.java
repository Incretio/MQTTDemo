package ru.incretio.mzto.consumer.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

public class Config {
    private static String brokerServer;
    private static String topicName;

    static {
        updateParametersFromResource();

        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("client.conf")) {
            prop.load(input);
            brokerServer = prop.getProperty("broker.server", brokerServer);
            topicName = prop.getProperty("topic.name", topicName);
        } catch (IOException ex) {
            // do nothing
        }
    }

    private static void updateParametersFromResource() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        brokerServer = resourceBundle.getString("broker.server");
        topicName = resourceBundle.getString("topic.name");
    }

    public static String getBrokerServer() {
        return brokerServer;
    }

    public static String getTopicName() {
        return topicName;
    }
}