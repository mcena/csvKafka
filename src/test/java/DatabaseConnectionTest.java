import com.mcena.Object.Person;
import com.mcena.datasource.DBConnection;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Beans.xml");
        DBConnection dbConnection = (DBConnection) applicationContext.getBean("databaseConnection");

        Person person = new Person("anotherFirstName", "anotherLastName","anotherEmail@yahoo.com", "938493");

        dbConnection.insertData(person);

    }
}
