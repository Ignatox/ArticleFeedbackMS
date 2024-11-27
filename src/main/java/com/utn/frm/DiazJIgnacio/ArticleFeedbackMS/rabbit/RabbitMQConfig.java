package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //Order placed queue y exchange
    public static final String SELL_FLOW_EXCHANGE = "sell_flow";
    public static final String ORDER_PLACED_QUEUE = "order_placed_queue";
    public static final String ORDER_PLACED_ROUTING_KEY = "order_placed";

    //Update likes queue y exchange

    public static final String UPDATE_LIKES_QUEUE = "update_likes_queue";
    public static final String UPDATE_LIKES_EXCHANGE = "update_likes_exchange";

    //Exchange de OrderService
    @Bean
    public TopicExchange sellFlowExchange(){
        return new TopicExchange(SELL_FLOW_EXCHANGE);

    }
    //Declaro la cola para recibir mensajes order placed
    @Bean
    public Queue orderPlacedQueue(){
        return new Queue(ORDER_PLACED_QUEUE, true);
    }

    //Vinculo la cola al exchange con la clave de enrutamiento
    @Bean
    public Binding orderPlacedBinding(Queue orderPlacedQueue, TopicExchange sellFlowExchange){
        return BindingBuilder.bind(orderPlacedQueue)
                .to(sellFlowExchange)
                .with(ORDER_PLACED_ROUTING_KEY);
    }

    //Direct Exchange de updateLikes
    @Bean
    public DirectExchange updateLikesExchange(){
        return new DirectExchange(UPDATE_LIKES_EXCHANGE);
    }
    //Queue de updateLikes
    @Bean
    public Queue updateLikesQueue() {
        return new Queue(UPDATE_LIKES_QUEUE, true);
    }

    //Binding de updatelikes
    @Bean
    public Binding bindingUpdateLikesQueue(Queue updateLikesQueue, DirectExchange updateLikesExchange) {
        return BindingBuilder.bind(updateLikesQueue).to(updateLikesExchange).with(UPDATE_LIKES_QUEUE);
    }

     //Parseo de mensajes a formato JSON
    @Bean
    public MessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("*"); // Permite confiar en todos los paquetes
        converter.setClassMapper(typeMapper);
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }


}





