package util.commands;

import util.person.Person;
import util.sendingUtils.Response;
import server.DatagramServer;
import server.list.PersonList;

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
