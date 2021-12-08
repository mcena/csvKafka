import com.mcena.csv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class CSVReaderApplicationContext {

    public static void main(String[] args) throws IOException {
        final Logger logger = LoggerFactory.getLogger(CSVReaderApplicationContext.class);

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Beans.xml");
        CSVReader csvReader = (CSVReader) applicationContext.getBean("CSVReader");
        try {
            // entry point
            csvReader.readCSVFile();
        } catch (Exception e) {
            logger.error("Exception occured when executing method readCSVFile(): " + e.getMessage());
        }

    }
}
