package util.commands;

import util.exceptions.InvalidCommandException;
import server.list.PersonList;
import util.person.Person;
import server.DatagramServer;
import util.sendingUtils.Response;

import java.util.List;
import java.util.stream.Collectors;

public class RemoveById extends Command {

    public Response execute(List<Object> params){
        String title = "Success";
        String message = "";
        List<Person> forRemoving;
        PersonList personList = (PersonList) params.get(0);
        int arg = (int) params.get(1);
        forRemoving = personList.getList().stream().
                filter(s -> s.getId() == arg).
                collect(Collectors.toList());
        try {
            if (!forRemoving.isEmpty()) {
                for (Person s : forRemoving) {
                    personList.removePerson(s);
                }
                message = "Element deleted";
            } else {
                message = "No such elements found";
                throw new InvalidCommandException("Element with this id doesn't exists");
            }
        } catch (InvalidCommandException e){
            System.out.println(e.getMessage());
        }
        DatagramServer.logger.info("Command RemoveById completed");
        return new Response(title, message);
    }
}
