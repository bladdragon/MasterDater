package com.example.austin.masterdater;

/**
 * Created by njaunich on 2/18/16.
 */
import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;


public class ExampleDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "example.austin.masterdater"); //Scheme for GreenDAO ORM
        createDB(schema);
        new DaoGenerator().generateAll(schema, "./app/src/main/java/");
        //where you want to store the generated classes.
    }

    private static void createDB(Schema schema) {

        Entity User = schema.addEntity("User");
        User.addIdProperty();
        User.addStringProperty("Name");
        User.addIntProperty("PhoneNumber");

        Entity CalendarEvent = schema.addEntity("CalendarEvent");
        CalendarEvent.addIntProperty("Id");
        CalendarEvent.addDateProperty("Date");
        CalendarEvent.addIntProperty("Time");
        CalendarEvent.addBooleanProperty("Free");

        CalendarEvent.getProperties().get(0);

        User.addToMany(CalendarEvent, CalendarEvent.getProperties().get(0));





    }

}