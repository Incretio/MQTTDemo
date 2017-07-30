package ru.incretio.mzto.publisher.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

public class Config {
    private static String brokerServer;
    private static String topicName;
    private static String directoryUri;

    static {
        updateParametersFromResource();

        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("server.conf")) {
            prop.load(input);
            brokerServer = prop.getProperty("broker.server", brokerServer);
            topicName = prop.getProperty("topic.name", topicName);
            directoryUri = prop.getProperty("directory.uri", directoryUri);
        } catch (IOException ex) {
            // do nothing
        }
    }

    private static void updateParametersFromResource(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        brokerServer = resourceBundle.getString("broker.server");
        topicName = resourceBundle.getString("topic.name");
        directoryUri = resourceBundle.getString("directory.uri");
    }

    public static String getBrokerServer() {
        return brokerServer;
    }

    public static String getTopicName() {
        return topicName;
    }

    public static String getDirectoryUri() {
        return directoryUri;
    }
}
