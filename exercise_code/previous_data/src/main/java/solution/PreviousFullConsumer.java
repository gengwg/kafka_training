package solution;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;


public class PreviousFullConsumer {
  public static void main(String[] args) {
             Properties props = new Properties();
             props.put("bootstrap.servers", "broker1:9092");
             props.put("group.id", "mygroup");
             props.put("enable.auto.commit", "true");
             props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
             props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
             KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
             consumer.subscribe(Arrays.asList("hello_world_topic"));
             consumer.poll(0);
             consumer.seekToBeginning(consumer.assignment());
             
             while (true) {
                 ConsumerRecords<String, String> records = consumer.poll(100);
                 for (ConsumerRecord<String, String> record : records)
                     System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
             }
  }
}

