package main;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ExceptionStoreProducerApp {
	
	public void sendException(Exception e, String hostname, String user, Date date) {
		ExceptionInfo exceptionInfo = new ExceptionInfo(e, hostname, user, date);
		String message = toJsonStr(exceptionInfo);
		try {
			MyKafkaProducer.runProducer(1, message);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public String toJsonStr(ExceptionInfo exceptionInfo) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String str = null;
		try {
			str = ow.writeValueAsString(exceptionInfo);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return str;
	}
}
