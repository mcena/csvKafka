import com.mcena.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerRunner {
    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(ConsumerRunner.class);
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Beans.xml");
        Consumer consumer = (Consumer) applicationContext.getBean("Consumer");

        // initialize consumer
        try {
            consumer.initConsumer();
        } catch (Exception e) {
            logger.error("Exception occurred when executing method initConsumer(): " + e.getMessage());
        }

    }
}
