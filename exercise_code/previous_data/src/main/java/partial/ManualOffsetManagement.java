package partial;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

public class ManualOffsetManagement {
	public static void main(String[] args) throws IOException {
		final String OFFSET_FILE_PREFIX = "offset_";

		Properties props = new Properties();
		props.put("bootstrap.servers", "broker1:9092");
		props.put("group.id", "mygroup");
		props.put("enable.auto.commit", "false");
		props.put("max.poll.records", "10");
		props.put("auto.offset.reset", "earliest");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
			consumer.subscribe(Arrays.asList("hello_world_topic"));
			consumer.poll(0);
			// TODO: For each partition, read the stored offset if there is one
			//       and seek to that offset
			for (TopicPartition partition : consumer.assignment()) {
				if (Files.exists(Paths.get(OFFSET_FILE_PREFIX + partition.partition()))) {
					long offset = Long
							.parseLong(Files.readAllLines(Paths.get(OFFSET_FILE_PREFIX + partition.partition()),
									Charset.defaultCharset()).get(0));
					consumer.seek(partition, offset);
				}

			}

			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					System.out.format("Offset = %s, Key = %s, Value = %s\n", record.offset(), record.key(),
							record.value());
					// TODO: Write the new offset for this record. Remember to write to the file for the relevant partition
					Files.write(Paths.get(OFFSET_FILE_PREFIX + record.partition()),
							Long.valueOf(record.offset() + 1).toString().getBytes());
				}

			}
		}
	}
}
