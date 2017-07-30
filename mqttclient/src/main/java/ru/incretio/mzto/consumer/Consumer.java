package ru.incretio.mzto.consumer;

import org.eclipse.paho.client.mqttv3.*;
import ru.incretio.mzto.consumer.utils.Utils;

class Consumer {
    private final MqttCallback mqttCallback;
    private final String serverURI;
    private final String topicName;

    private MqttClient mqttClient;

    public Consumer(MqttCallback mqttCallback, String serverURI, String topicName) {
        this.mqttCallback = mqttCallback;
        this.serverURI = serverURI;
        this.topicName = topicName;
    }

    public void start() {
        try {
            mqttClient = new MqttClient(serverURI, Utils.getMacAddress());
            mqttClient.setCallback(mqttCallback);
            mqttClient.connect(getOptionConnection());
            mqttClient.subscribe(topicName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MqttConnectOptions getOptionConnection() {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(false);
        return connectOptions;
    }

    public void stop() {
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
