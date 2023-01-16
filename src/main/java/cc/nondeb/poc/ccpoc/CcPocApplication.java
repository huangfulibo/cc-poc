package cc.nondeb.poc.ccpoc;

import cc.nondeb.poc.ccpoc.utils.ClientConfigurationUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static cc.nondeb.poc.ccpoc.utils.ClientConfigurationUtils.CONFIG_FILE;

@SpringBootApplication
public class CcPocApplication {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(CcPocApplication.class, args);
//		System.out.println("begin to send event to confluent cloud");
//		Properties props =
//				ClientConfigurationUtils.loadConfig(CONFIG_FILE);
//		KafkaProducer producer = new KafkaProducer(props);
//		long i = 0;
//		while (true) {
//			Thread.sleep(2000);
//			i++;
//			RecordMetadata recordMetadata = (RecordMetadata) producer.send
//							(new ProducerRecord<>("badi-poc", "key"+i, "value"+i))
//					.get();
//			RecordMetadata record = (RecordMetadata) producer.send
//							(new ProducerRecord<>("badi-poc-cd", "key"+i, "value"+i))
//					.get();
//			System.out.println(String.format("Event published:key=%s, value=%s, metadata=%s","key"+i,
//					"value"+i, recordMetadata));
//			System.out.println(String.format("Event published:key=%s, value=%s, metadata=%s","key"+i,
//					"value"+i, record));
//		}
		System.out.println("begin to consume event from confluent cloud");
		final Properties props =
				ClientConfigurationUtils.loadConfig(CONFIG_FILE);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "cc-poc");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Arrays.asList("badi-poc", "badi-poc-cd"));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, String> record : records) {
				System.out.println(String.format("Event subscribed:topic=%s, partition=%d, offset=%d, key=%s, value=%s",
						record.topic(), record.partition(), record.offset(), record.key(), record.value()));
				Thread.sleep(10);
			}
		}

	}

}
