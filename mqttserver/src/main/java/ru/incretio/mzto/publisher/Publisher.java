package ru.incretio.mzto.publisher;

import org.eclipse.paho.client.mqttv3.*;

import static java.nio.file.StandardWatchEventKinds.*;

class Publisher {
    private final String serverURI;
    private final String directory;
    private final String topicName;

    private MqttClient mqttClient;
    private Thread watcherThread;

    public Publisher(String serverURI, String directory, String topicName) {
        this.serverURI = serverURI;
        this.directory = directory;
        this.topicName = topicName;
    }

    public void start() {
        try {
            mqttClient = new MqttClient(serverURI, MqttClient.generateClientId());
            mqttClient.connect();
            startWatcher(mqttClient);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void startWatcher(MqttClient mqttClient){
        EventHandler eventHandler = (kind, fileName) -> {
            String messageData;
            if (kind == ENTRY_CREATE) {
                messageData = "Добавлен файл " + fileName;
            } else if (kind == ENTRY_DELETE) {
                messageData = "Удалён файл " + fileName;
            } else {
                messageData = "Что-то произошло с файлом " + fileName;
            }
            MqttMessage message = new MqttMessage();
            message.setPayload(messageData.getBytes());
            try {
                mqttClient.publish(topicName, message);
                System.out.println("Folder changed.");
            } catch (MqttException e) {
                e.printStackTrace();
            }
        };

        WatcherDirectory watcherDirectory = new WatcherDirectory(eventHandler);
        watcherThread = new Thread(() -> {
            try {
                watcherDirectory.start(directory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        watcherThread.start();
    }

    public void stop(){
        if (watcherThread != null){
            watcherThread.interrupt();
        }
        if (mqttClient != null) {
            try {
                mqttClient.disconnect();
                mqttClient.close();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
}


