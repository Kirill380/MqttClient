import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class Config {

    private static MqttConnectOptions connOpts;
    public static final int QoS = 2;
    public static final String BROKER = "tcp://localhost:1884";

    private Config() {

    }

    public static MqttConnectOptions getOptions() {
        if(connOpts == null) {
            connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("testuser");
            connOpts.setPassword("passwd".toCharArray());
        }
        return connOpts;

    }

}
