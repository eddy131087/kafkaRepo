package com.example.demo.producer.controller;

import com.example.demo.producer.model.ProductRequest;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ListenerController {
  @KafkaListener(
      topics = "${spring.kafka.source.topic}",
      containerFactory = "kafkaListenerContainerFactory"
  )
  void listener(String data){
    try{
      log.info("\nData received: " + data);
      ObjectMapper mapper = new ObjectMapper();
      JsonParser parser = mapper.createParser(data);
      ProductRequest product =  parser.readValueAs(ProductRequest.class);
      log.info("\n");
      log.info("\nPRODUCT=" + product.toString());
      log.info("\n");
    }catch(Exception e){
      log.error("Exception:",e);
    }
  }
}
