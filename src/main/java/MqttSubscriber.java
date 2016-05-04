import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MqttSubscriber implements MqttCallback {
    private static final Logger LOG = LoggerFactory.getLogger(MqttSubscriber.class);
    private final String topic = "examples";
    private final String clientId = "JavaSubscriber";
    private MqttClient client;


    public static void main(String[] args) throws IOException {
        new MqttSubscriber().runSubscriber();
    }

    public void runSubscriber() throws IOException {
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            client = new MqttClient(Config.BROKER, clientId, persistence);
            client.setCallback(this);
            client.connect(Config.getOptions());
            client.subscribe(topic, Config.QoS);

            System.in.read();

            client.disconnect();


        } catch (MqttException me) {
            LOG.error("Mqtt client failed due to: " + me.getMessage(), me);
            System.exit(1);
        }

    }


    public void connectionLost(Throwable e) {
        LOG.error("Error: " + e.getMessage(), e);
        System.exit(1);
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        LOG.info("Arrived message: " + message.toString());
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
