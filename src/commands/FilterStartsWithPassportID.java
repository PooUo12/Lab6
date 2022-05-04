package commands;

import list.PersonList;
import person.Person;
import server.DatagramServer;
import util.Response;

import java.util.List;
import java.util.stream.Collectors;

public class FilterStartsWithPassportID extends Command {

    public Response execute(List<Object> params){
        PersonList personList = (PersonList) params.get(0);
        String arg = (String) params.get(1);
        List<Person> output;
        output = personList.getList().stream().
                filter(s -> s.getPassportID().startsWith(arg)).
                collect(Collectors.toList());

        DatagramServer.logger.info("Command FilterStartsWithPassportID completed");
        return new Response("Success",output.toString());
    }
}
