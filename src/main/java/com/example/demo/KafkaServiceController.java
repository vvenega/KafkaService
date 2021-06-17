package com.example.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



import org.springframework.web.bind.annotation.PathVariable;

@RestController

public class KafkaServiceController {
	
	//private final static String VALID_CUSTOMER = "http://192.168.1.66:4200";
	private final static String VALID_CUSTOMER = "*";
	private final static String SERVER = "localhost:9092";
	private final static String TOPIC_EVENT = "eventtopic";
	private final static String TOPIC_CHAT = "productrequested";
	private static Producer<String, EventBean> producerEvent=null;
	private static Producer<String, ChatBean> producerChat=null;
	public final static Logger logger = LoggerFactory.getLogger(KafkaServiceController.class.getName());
	private final static String ADVERTISED_HOST_NAME_VALUE = "localhost";
	private final static String ADVERTISED_HOST_NAME_KEY ="advertised.host.name";

	@CrossOrigin(origins = VALID_CUSTOMER)
	@GetMapping("/RecordEvent/{owner}/{price}/{category}/{type}/{name}/{user}/{objectid}/{event}")
	
	public boolean setEvent(@PathVariable String owner,@PathVariable double price,
			@PathVariable String category,@PathVariable String type, @PathVariable String name, 
			@PathVariable String user,@PathVariable long objectid,@PathVariable String event) {
		boolean result = false;
		
		producerEvent = createEventProducer();
		
		try {
			
			EventBean bean = new EventBean();
			bean.setOwner(owner);
			bean.setPrice(price);
			bean.setCategory(category);
			bean.setType(type);
            bean.setName(name);
            bean.setUser(user);
            bean.setObjectid(objectid);
            bean.setEvent(event);
            bean.setViewclick(true);
            bean.setContactclick(false);
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            bean.setDatetime(dtf.format(now));
            
            //System.out.println(dtf.format(now));
	    	
	    	ProducerRecord<String, EventBean> recordToSend = new ProducerRecord<String,EventBean>(TOPIC_EVENT, bean.getObjectid()+"", bean);
	        
	    	producerEvent.send(recordToSend, (recordMetadata, e) -> {
	              if (e == null) {
	                  logger.info("Message Sent. topic={}, partition={}, offset={}", recordMetadata.topic(),
	                          recordMetadata.partition(), recordMetadata.offset());
	                  
	                  
	              } else {
	                  logger.error("Error while sending message. ", e);
	                  
	              }
	          });
	        
	    	result = true;
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		
		
		closeEventProducer();
		
		return result;
	}
	
	
@CrossOrigin(origins = VALID_CUSTOMER)
@GetMapping("/RecordChat/{owner}/{nameowner}/{requester}/{namerequester}/{objectid}/{price}/{product}/{idconversation}")
	
	public boolean setChat(@PathVariable String owner,@PathVariable String nameowner,
			@PathVariable String requester,@PathVariable String namerequester,
			@PathVariable String objectid,@PathVariable String price,
			@PathVariable String product,@PathVariable String idconversation) {
	
	System.err.println("RecordChat....{"+product+"}");
		boolean result = false;
		
		producerChat = createChatProducer();
		
		try {
			
			ChatBean bean = new ChatBean();
			bean.setOwner(owner);
			bean.setNameowner(nameowner);
			bean.setRequester(requester);
			bean.setNamerequester(namerequester);
			bean.setObjectid(Long.parseLong(objectid));
			bean.setProduct(product);
			bean.setPrice(Double.parseDouble(price));
			bean.setIdconversation(idconversation);
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            bean.setDaterequested(dtf.format(now));
            

	    	
	    	ProducerRecord<String, ChatBean> recordToSend = new ProducerRecord<String,ChatBean>(TOPIC_CHAT, bean.getIdconversation(), bean);
	        
	    	producerChat.send(recordToSend, (recordMetadata, e) -> {
	              if (e == null) {
	                  logger.info("Message Sent. topic={}, partition={}, offset={}", recordMetadata.topic(),
	                          recordMetadata.partition(), recordMetadata.offset());
	                  
	                  
	              } else {
	                  logger.error("Error while sending message. ", e);
	                  
	              }
	          });
	        
	    	result = true;
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		
		
		closeChatProducer();
		
		return result;
	}
	
	

	@CrossOrigin(origins = VALID_CUSTOMER)	
@GetMapping("/RecordEvent/{owner}/{price}/{category}/{type}/{name}/{user}/{objectid}/{event}/{viewclick}/{contactclick}")
	
	public boolean setEvent(@PathVariable String owner,@PathVariable double price,
			@PathVariable String category,@PathVariable String type, @PathVariable String name, 
			@PathVariable String user,@PathVariable long objectid,@PathVariable String event,
			@PathVariable boolean viewclick, @PathVariable boolean contactclick ) {
		boolean result = false;
		
		producerEvent = createEventProducer();
		
		try {
			
			EventBean bean = new EventBean();
			bean.setOwner(owner);
			bean.setPrice(price);
			bean.setCategory(category);
			bean.setType(type);
            bean.setName(name);
            bean.setUser(user);
            bean.setObjectid(objectid);
            bean.setEvent(event);
            bean.setViewclick(viewclick);
            bean.setContactclick(contactclick);
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            bean.setDatetime(dtf.format(now));
            
            System.err.println("price:"+price);
            System.err.println("category:"+category);
            System.err.println("type:"+type);
            System.err.println("owner:"+owner);
            
            
            //System.out.println(dtf.format(now));
	    	
	    	ProducerRecord<String, EventBean> recordToSend = new ProducerRecord<String,EventBean>(TOPIC_EVENT, bean.getObjectid()+"", bean);
	        
	    	producerEvent.send(recordToSend, (recordMetadata, e) -> {
	              if (e == null) {
	                  logger.info("Message Sent. topic={}, partition={}, offset={}", recordMetadata.topic(),
	                          recordMetadata.partition(), recordMetadata.offset());
	                  
	                  
	              } else {
	                  logger.error("Error while sending message. ", e);
	                  
	              }
	          });
	        
	    	result = true;
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		
		
		closeEventProducer();
		
		return result;
	}
	
	private  Producer<String, EventBean> createEventProducer() {
	    Properties kafkaProps = new Properties();
	    kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
	    kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	    kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EventBeanSerializer.class.getName());
	    kafkaProps.put(ADVERTISED_HOST_NAME_KEY, ADVERTISED_HOST_NAME_VALUE);
	    return new KafkaProducer<String, EventBean>(kafkaProps);
	}
	
	private  Producer<String, ChatBean> createChatProducer() {
	    Properties kafkaProps = new Properties();
	    kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
	    kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	    kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ChatBeanSerializer.class.getName());
	    kafkaProps.put(ADVERTISED_HOST_NAME_KEY, ADVERTISED_HOST_NAME_VALUE);
	    return new KafkaProducer<String, ChatBean>(kafkaProps);
	}
	
	
	private void closeEventProducer() {
		
		if(producerEvent!=null) {
			producerEvent.flush();
    		producerEvent.close();
    	}
	}
	
   private void closeChatProducer() {
		
		if(producerChat!=null) {
			producerChat.flush();
			producerChat.close();
    	}
	}
	
}
