package com.mcena.csv;

import com.mcena.Object.Person;
import com.mcena.producer.Producer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private final Logger logger = LoggerFactory.getLogger(CSVReader.class);

    private final String csvFileLocation;
    private final Producer producer;
    private List<Person> personList;

    public CSVReader(String csvFileLocation, Producer producer) {
        this.csvFileLocation = csvFileLocation;
        this.producer = producer;
        this.personList = new ArrayList<Person>();
    }

    public void readCSVFile() {
        logger.info("initializing CSV reader..");
        try {
            Reader reader = new FileReader(csvFileLocation);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader()
                    .parse(reader);
            for(CSVRecord record : records) {
                // get CSV record per row
                String firstName = record.get(CSVHeaders.firstName);
                String lastName = record.get(CSVHeaders.lastName);
                String email = record.get(CSVHeaders.email);
                String phoneNumber = record.get(CSVHeaders.phone);

                // convert to object
                Person person = new Person(firstName, lastName, email, phoneNumber);
                logger.info("CSV Record fetched: " + person.toString());
                personList.add(person);
            }
        } catch (Exception e) {
            logger.error("Exception occurred while reading CSV: " + e.getMessage());
        }
        // pass to Producer class to perform kafka operation
        producer.initProducer(personList);
    }
}
