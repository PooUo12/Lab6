package server.listfiller;

import util.exceptions.IllegalFieldsException;

import server.list.PersonList;
import client.parsers.ColorRecognizer;
import util.person.Color;
import util.person.Coordinates;
import util.person.Location;
import util.person.Person;

import java.time.ZonedDateTime;


public class PersonCreator {

    private final PersonList personList;
    public PersonCreator(PersonList personList){
        this.personList = personList;
    }

    public Person createPerson(String s) {
        String[] args;
        Person person = null;

        args = s.split(",");
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("")) {
                args[i] = null;
            }
        }
        try {
            int id;
            ZonedDateTime creationDate;
            if (args[0] == null){
                id = personList.maxId()+ 1;
            } else {
                id = Integer.parseInt(args[0]);
                if (personList.compareIds(id)){
                    id = personList.maxId() + 1;
                }
            }
            String personName = args[1];
            int coordinatesX = Integer.parseInt(args[2]);
            Long coordinatesY = Long.parseLong(args[3]);
            if (args[4] == null){
                creationDate = ZonedDateTime.now();
            } else {
                creationDate = ZonedDateTime.parse(args[4]);
            }
            int personHeight = Integer.parseInt(args[5]);
            double personWeight = Double.parseDouble(args[6]);
            String passportID = args[7];
            ColorRecognizer colorRecognizer = new ColorRecognizer(args[8]);
            Color colorHair = colorRecognizer.stringToColor();
            Double locationX = Double.parseDouble(args[9]);
            Double locationY = Double.parseDouble(args[10]);
            float locationZ = Float.parseFloat(args[11]);
            String locationName = args[12];


            if (personName.length() > 0 && personHeight > 0 && personWeight > 0 && passportID.length() >= 8 && locationName.length() <= 233 && colorHair != null) {
                Coordinates coordinates = new Coordinates(coordinatesX, coordinatesY);
                Location location = new Location(locationX, locationY, locationZ, locationName);
                person = new Person(id, personName, coordinates, creationDate ,personHeight, personWeight, passportID, colorHair, location);

            } else {
                throw new IllegalFieldsException("Impossible to create person because of incorrect args");
            }
        } catch (IllegalFieldsException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid args format");
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Null argument where impossible");
        }
        return person;
    }


}
