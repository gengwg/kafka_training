package partial;

import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Cluster;

public class CustomPartitioner {
	public static class MyPartitioner implements Partitioner {

		@Override
		public void configure(Map<String, ?> configs) {
		}

		@Override
		public void close() {
		}

		@Override
		public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes,
				Cluster cluster) {
			// TODO: Your partitioner logic goes here!
		}

	}

	public void createProducer() {
		long numberOfEvents = 50;
		Random rand = new Random();

		Properties props = new Properties();
		// Configure brokers to connect to
		props.put("bootstrap.servers", "broker1:9092");
		// Configure serializer class
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		// Configure serializer class
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		// Configure paritioner
		props.put("partitioner.class", "solution.CustomPartitioner$MyPartitioner");

		Producer<String, String> producer = new KafkaProducer<>(props);
		for (int i = 0; i < numberOfEvents; i++) {
			producer.send(new ProducerRecord<String, String>("two-p-topic", String.valueOf(rand.nextInt(19) + 1)));
		}

		producer.close();

	}

	public static void main(String[] args) {
		CustomPartitioner customPartitioner = new CustomPartitioner();
		customPartitioner.createProducer();
	}
}
