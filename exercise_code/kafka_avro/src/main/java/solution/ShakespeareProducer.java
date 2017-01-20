package solution;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ShakespeareProducer {
	/**
	 * Reads in every line of the input file and sends it with a KafkaProducer
	 * 
	 * @param inputFile
	 *            The file to read in
	 * @throws IOException
	 */
	public void runProducer(File inputFile) throws IOException {
		KafkaProducer<String, String> producer = createProducer();

		if (inputFile.isDirectory()) {
			// If a directory, iterate through all files
			for (File fileInDirectory : inputFile.listFiles()) {
				sendFile(fileInDirectory, producer);
			}
		} else {
			// If a single file, send it
			sendFile(inputFile, producer);
		}
		producer.close();
	}

	private void sendFile(File inputFile, KafkaProducer<String, String> producer)
			throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));

		// Use the file name as the key
		String key = inputFile.getName().split("\\.")[0];
		
		String line = null;

		// Read in the file line by line and send it
		while ((line = reader.readLine()) != null) {
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(
				"shakespeare_topic", key, line);
			producer.send(record);
		}

		reader.close();
		
		System.out.println("Finished producing file:" + inputFile.getName());
	}

	/**
	 * Creates the KafkaProducer and configures it
	 * 
	 * @return The configured KafkaProducer
	 */
	private KafkaProducer<String, String> createProducer() {
		Properties props = new Properties();
		// Configure brokers to connect to
		props.put("bootstrap.servers", "broker1:9092");
		// Configure serializer classes
		props.put("key.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");

		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(
				props);
		return producer;
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Must add file path as an argument");
			System.exit(-1);
		}

		try {
			ShakespeareProducer helloProducer = new ShakespeareProducer();
			helloProducer.runProducer(new File(args[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
