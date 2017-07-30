package ru.incretio.mzto.consumer;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Date;

class SimpleMqttCallBack implements MqttCallback {

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Соединение в MQTT сервером потеряно!");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String messageData = new String(mqttMessage.getPayload());
        String outputText = String.format("%s: %n\t%s", new Date(), messageData);
        System.out.println(outputText);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // do nothing
    }

}
