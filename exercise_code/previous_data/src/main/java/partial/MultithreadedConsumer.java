package partial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

public class MultithreadedConsumer {

	private static class ConsumerLoop implements Runnable {
		private final KafkaConsumer<String, String> consumer;
		private final List<String> topics;
		private final int id;

		public ConsumerLoop(int id, String groupId, List<String> topics) {
			// TODO: Create a new KafkaConsumer
			this.id = id;
			this.topics = topics;
			Properties props = new Properties();
			props.put("bootstrap.servers", "broker1:9092");
			props.put("group.id", groupId);
			props.put("key.deserializer",StringDeserializer.class.getName());
			props.put("value.deserializer",StringDeserializer.class.getName());
			this.consumer = new KafkaConsumer<>(props);
		}

		@Override
		public void run() {
			// TODO: Subscribe to the topics, then loop round reading and displaying messages
			try {
				consumer.subscribe(topics);
				while (true) {
					ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
					for (ConsumerRecord<String, String> record: records){
						System.out.format("Thread = %d, Partition = %d, Offset = %s, Key = %s, Value = %s\n", id,
								record.partition(), record.offset(), record.key(), record.value());}
				}
			} catch (WakeupException e) {
				// ignore for shutdown
			} finally {
				consumer.close();
			}
		}

		public void shutdown() {
			consumer.wakeup();
		}
	}

	public static void main(String[] args) {
		int numConsumers = 4;
		String groupId = "myGroup";
		List<String> topics = Arrays.asList("two-p-topic");
		ExecutorService executor = Executors.newFixedThreadPool(numConsumers);

		final List<ConsumerLoop> consumers = new ArrayList<>();
		for (int i = 0; i < numConsumers; i++) {
			ConsumerLoop consumer = new ConsumerLoop(i, groupId, topics);
			consumers.add(consumer);
			executor.submit(consumer);
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				for (ConsumerLoop consumer : consumers) {
					consumer.shutdown();
				}
				executor.shutdown();
				try {
					executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
