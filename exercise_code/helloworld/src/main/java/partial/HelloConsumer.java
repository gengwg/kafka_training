package partial;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


public class HelloConsumer {
  public static void main(String[] args) {
	     Properties props = new Properties();
	     props.put("bootstrap.servers", "broker1:9092");
	     props.put("group.id", "testgroup");
	     props.put("enable.auto.commit", "true");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("session.timeout.ms", "30000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     
	     // TODO: Create a new consumer, with the properties we've created above

	    // TODO: Subscribe to your topic here

	     while (true) {
	         // TODO: Read records using the poll() method 
	    	 
	    	 // TODO: Loop around the records, printing out each record.offset, record.key, and record.value
	     }
  }

}
