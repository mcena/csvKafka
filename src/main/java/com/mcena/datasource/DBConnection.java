package com.mcena.datasource;

import com.mcena.Object.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DBConnection implements IDBConnection{
    private final Logger logger = LoggerFactory.getLogger(DBConnection.class);
    private final DataSource dataSource;

    // insert query
    private final String SQL_INSERT_QUERY = "INSERT INTO Persons (first_name, last_name, email, phone) VALUES (?,?,?,?)";

    public DBConnection(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public <T> void initQuery(T genericObject) {
        logger.info("Initializing INSERT SQL query");
        Person person = (Person) genericObject;

        // execute insert query
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_QUERY)) {

            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2,person.getLastName());
            preparedStatement.setString(3,person.getEmail());
            preparedStatement.setString(4,person.getPhoneNumber());

            // execute sql query
            preparedStatement.executeUpdate();

            logger.info("SQL Query successful, records inserted to database: " + person.toString());

        } catch (Exception e) {
            logger.error("Error when executing SQL operation: " + e.getMessage());
        }
    }
}
