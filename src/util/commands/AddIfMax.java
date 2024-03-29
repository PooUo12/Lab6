package util.commands;

import util.person.Person;
import util.sendingUtils.Response;
import server.DatagramServer;
import server.list.PersonList;

import java.util.List;

public class AddIfMax extends Command {

    public Response execute(List<Object> params){
        String title = "";
        String message;
        PersonList personList = (PersonList) params.get(0);
        Person person = (Person) params.get(1);
        try{
            if (person.compareTo(personList.getList().peekLast()) > 0){
                personList.getList().add(person);
                title = "Success";
                message = "Element was added";
            } else {
                message = "Element wasn't added(not biggest)";
            }
            DatagramServer.logger.info("Command AddIfMax completed");
        }catch (NullPointerException e){
            System.out.println("Can't compare because server.list is empty");
            title = "Error";
            message = "List is empty";
            DatagramServer.logger.info("Command AddIfMax couldn't be completed");
        }
        return new Response(title,message);
    }
}
