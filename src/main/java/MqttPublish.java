import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;


public class MqttPublish {

    private static final Logger LOG = LoggerFactory.getLogger(MqttPublish.class);

    public static void main(String[] args) throws InterruptedException {
        String topic = "examples";
        String content;
        String clientId = "JavaPublisher";
        MqttClient client;
        MemoryPersistence persistence = new MemoryPersistence();

        String name = RandomStringUtils.randomAscii(5);
        boolean stop = false;

        try {
            client = new MqttClient(Config.BROKER, clientId, persistence);
            client.connect(Config.getOptions());

            do {
                content = name + "___" + RandomStringUtils.randomAscii(20);
                LOG.info(content);
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(Config.QoS);

                client.publish(topic, message);

                Thread.sleep(3000);

            } while (!stop);

            client.disconnect();
            System.exit(0);

        } catch (MqttException me) {
            LOG.error("Mqtt client failed due to: " + me.getMessage(), me);
            System.exit(1);
        }
    }


}
