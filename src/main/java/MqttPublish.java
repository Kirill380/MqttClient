import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class MqttPublish {

    private static final Logger LOG = LoggerFactory.getLogger(MqttPublish.class);

    public static void main(String[] args) throws InterruptedException {

        String topic = "examples";
        String content;
        int qos = 2;
        String broker = "tcp://localhost:1884";
        String clientId = "JavaSample";
        MqttClient client;
        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        String name = RandomStringUtils.randomAscii(5);
        boolean stop = false;


        try {

            do {
                client = new MqttClient(broker, clientId, persistence);

                client.connect(connOpts);

                content = name + "___" + RandomStringUtils.randomAscii(20);
                LOG.info(content);
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);

                client.publish(topic, message);

                client.disconnect();
                Thread.sleep(3000);

            } while (!stop);


            System.exit(0);

        } catch (MqttException me) {
            LOG.error("Mqtt client failed due to: " + me.getMessage(), me);
            System.exit(1);
        }
    }


}
