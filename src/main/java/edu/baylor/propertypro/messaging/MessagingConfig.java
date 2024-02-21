package edu.baylor.propertypro.messaging;

import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@Configuration
public class MessagingConfig {

    @Bean
    public Topic requestTopic() {
        return new ActiveMQTopic("propertypro.request.topic");
    }
    
    @Bean
    public Topic offerTopic() {
        return new ActiveMQTopic("propertypro.offer.topic");
    }
    
    @Bean
    public Topic responseTopic() {
        return new ActiveMQTopic("propertypro.response.topic");
    }
}