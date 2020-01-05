package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(1);
    }

    @Bean
    Receiver receiver(CountDownLatch latch) {
        return new Receiver(latch);
    }

    @Bean
    MessageListenerAdapter adaptor(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "handleMessage");
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter adaptor) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.addMessageListener(adaptor, new PatternTopic("test"));
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(Application.class);
        StringRedisTemplate template = context.getBean(StringRedisTemplate.class);
        CountDownLatch latch = context.getBean(CountDownLatch.class);
        template.convertAndSend("test", "hello from redis");
        latch.await();
        System.exit(0);
    }
}
