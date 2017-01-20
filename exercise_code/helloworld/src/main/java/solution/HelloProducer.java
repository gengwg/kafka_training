package solution;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class HelloProducer {
	public void createProducer() {
		long numberOfEvents = 5;

		Properties props = new Properties();
		// Configure brokers to connect to
		props.put("bootstrap.servers", "broker1:9092");
		// Configure serializer classes
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(
				props);

		for (int i = 0; i < numberOfEvents; i++) {
			String key = "firstkey";
			String value = "firstvalue:" + i;
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(
					"hello_world_topic", key, value);
			producer.send(record);
		}

		producer.close();
	}

	public static void main(String[] args) {
		HelloProducer helloProducer = new HelloProducer();
		helloProducer.createProducer();
	}
}
