package partial;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import shakespeare_example.SpecificAvroSerde;
import shakespeare_example.model.ShakespeareKey;
import shakespeare_example.model.ShakespeareValue;

public class OutputAvroTopic {
	public static void main(String[] args) throws Exception {
		Properties streamsConfiguration = new Properties();
		streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "shakesreader");
		streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "broker1:9092");
		streamsConfiguration.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "zookeeper1:2181");
		streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		streamsConfiguration.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
		streamsConfiguration.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
		streamsConfiguration.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://schemaregistry1:8081");

		KStreamBuilder builder = new KStreamBuilder();

		// TODO: Create a KStream for the topic

		// TODO: Print out the data from the KStream

		KafkaStreams streams = new KafkaStreams(builder, streamsConfiguration);
		streams.start();
	}
}

