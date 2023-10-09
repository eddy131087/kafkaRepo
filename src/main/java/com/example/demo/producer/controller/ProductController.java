package com.example.demo.producer.controller;

import com.example.demo.producer.config.KafkaTopicConfig;
import com.example.demo.producer.model.ProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/product")
public class ProductController {
  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  private KafkaTopicConfig kafkaTopicConfig;
  @PostMapping("/create")
  public ResponseEntity<Boolean> createProduct(@RequestBody ProductRequest request) {
    try{
      ObjectMapper mapper = new ObjectMapper();
      ProducerRecord<String,String> producerRecord= new ProducerRecord<>(kafkaTopicConfig.productTopic().name(), mapper.writeValueAsString(request));
      kafkaTemplate.send(producerRecord);
      return new ResponseEntity<>(true, HttpStatus.OK);
    }catch (Exception e){
      log.error("Exception:",e);
      return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
  }
}
