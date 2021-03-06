package main;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class MyKafkaProducer {
	 private final static String TOPIC = "cloudurable";
	 private final static String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";
	 
	 private static Producer<Long, String> createProducer() {
	        Properties props = new Properties();
	        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
	                                            BOOTSTRAP_SERVERS);
	        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
	        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
	                                        LongSerializer.class.getName());
	        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
	                                    StringSerializer.class.getName());
	        return new KafkaProducer<>(props);
	 }
	 
	 static void runProducer(final int sendMessageCount, final String message) throws InterruptedException {
		    final Producer<Long, String> producer = createProducer();
		    long time = System.currentTimeMillis();
		    final CountDownLatch countDownLatch = new CountDownLatch(sendMessageCount);

		    try { long index = time;
//		        for (long index = time; index < time + sendMessageCount; index++) {
		            final ProducerRecord<Long, String> record =
		                    new ProducerRecord<>(TOPIC, index, message);
		            producer.send(record, (metadata, exception) -> {
		                long elapsedTime = System.currentTimeMillis() - time;
		                if (metadata != null) {
		                    System.out.printf("sent record(key=%s value=%s) " +
		                                    "meta(partition=%d, offset=%d) time=%d\n",
		                            record.key(), record.value(), metadata.partition(),
		                            metadata.offset(), elapsedTime);
		                } else {
		                    exception.printStackTrace();
		                }
		                countDownLatch.countDown();
		            });
//		        }
		        countDownLatch.await(25, TimeUnit.SECONDS);
		    }finally {
		        producer.flush();
		        producer.close();
		    }
	}
}
