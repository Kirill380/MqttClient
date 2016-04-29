import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttSubscriber implements MqttCallback {

    private static final Logger LOG = LoggerFactory.getLogger(MqttSubscriber.class);
    private final String topic = "examples";
    private final int qos = 2;
    private final String broker = "tcp://localhost:1884";
    private final String clientId = "JavaSubscriber";
    private MqttClient client;


    public static void main(String[] args) {
        new MqttSubscriber().runSubscriber();
    }

    public void runSubscriber() {
        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

        try {
            client = new MqttClient(broker, clientId, persistence);
            client.setCallback(this);
            client.connect(connOpts);
            client.subscribe(topic, qos);


            try {
                System.in.read();
            } catch (Exception e ) {

            }

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
