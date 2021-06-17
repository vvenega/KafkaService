package com.example.demo;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class EventBeanSerializer implements Serializer<EventBean>  {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	
	@Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

	@Override
	public byte[] serialize(String topic, EventBean data) {
		// TODO Auto-generated method stub
		
		try {
			
            //return objectMapper.writeValueAsBytes(data);
			return objectMapper.writeValueAsString(data).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	
	@Override
    public void close() {
    }
}