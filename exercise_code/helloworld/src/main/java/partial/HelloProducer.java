package partial;

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

		// TODO: Create a KafkaProducer
		KafkaProducer<String, String> producer = new KafkaProducer<>(props);

		for (int i = 0; i < numberOfEvents; i++) {
			String key = "firstkey";
			String value = "firstvalue:" + i;
			
			// TODO: Create the ProducerRecord
			ProducerRecord<String, String> record = new ProducerRecord<String, String> ("hello_world_topic", key, value);
			
			// TODO: Write the record
			producer.send(record);
					
		}

		//TODO: close the producer
		producer.close();
	}

	public static void main(String[] args) {
		HelloProducer helloProducer = new HelloProducer();
		helloProducer.createProducer();
	}
}
