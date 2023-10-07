// package com.social.app.config;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.Payload;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.messaging.simp.annotation.SendToUser;
// import org.springframework.messaging.simp.annotation.SubscribeMapping;
// import org.springframework.messaging.simp.config.MessageBrokerRegistry;
// import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
// import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
// import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// @Configuration
// @EnableWebSocketMessageBroker
// public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//     @Autowired
//     private SimpMessagingTemplate messagingTemplate;

//     @Override
//     public void configureMessageBroker(MessageBrokerRegistry config) {
//         config.enableSimpleBroker("/topic", "/user");
//         config.setApplicationDestinationPrefixes("/app");
//     }

//     @Override
//     public void registerStompEndpoints(StompEndpointRegistry registry) {
//         registry.addEndpoint("/chat").withSockJS();
//     }

// //    @MessageMapping("/sendPersonalMessage")
// //    @SendToUser("/topic/messages")
// //    public MessageResponse sendPersonalMessage(@Payload MessageRequest message) {
// //        // Add logic for handling personal messages and database interactions
// //        // Update the database with the new message, recipients, etc.
// //        // Send the message to the recipient
// //        return new MessageResponse(message.getTo(), message.getMessage());
// //    }
// //
// //    @SubscribeMapping("/chat/user/{userId}")
// //    public ChatHistoryResponse getUserChatHistory(@PathVariable String userId) {
// //        // Add logic to retrieve chat history for a user
// //        // Return a response containing the chat history
// //        return new ChatHistoryResponse(/* chat history data */);
// //    }
// }
