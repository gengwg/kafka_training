package partial;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;



import io.confluent.kafka.serializers.KafkaAvroDeserializer;

import partial.model.ShakespeareKey;
import partial.model.ShakespeareValue;

public class ShakespeareAvroConsumer {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "broker1:9092");
		props.put("group.id", "newgroup");

		// TODO: Set the key and value deserializers
		props.put("key.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
		props.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");

		props.put("schema.registry.url", "http://schemaregistry1:8081");
		props.put("specific.avro.reader", "true");
		props.put("auto.offset.reset", "earliest");



		KafkaConsumer<ShakespeareKey, ShakespeareValue> consumer = new KafkaConsumer<>(props);

		// TODO: Subscribe to shakespeare_avro_topic
		consumer.subscribe(Arrays.asList("shakespeare_avro_topic"));


		while (true) {
			ConsumerRecords<ShakespeareKey, ShakespeareValue> records = consumer.poll(100);
			for (ConsumerRecord<ShakespeareKey, ShakespeareValue> record : records) {

				// TODO: Extract the key and value into ShakespeareKey and ShakespeareValue objects
				ShakespeareKey shakespeareKey = record.key();
				ShakespeareValue shakespeareLine = record.value();
				
				// Output the information with the SpecificRecords

				// TODO: Write out the year, linenumber, and line
				
				System.out.println("From " + shakespeareKey.getWork() + " - "
						+ shakespeareKey.getYear() + " line:"
						+ shakespeareLine.getLineNumber() + " "
						+ shakespeareLine.getLine());
				
			}
		}
	}		
}
