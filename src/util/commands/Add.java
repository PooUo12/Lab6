package util.commands;

import server.list.PersonList;
import util.person.Person;
import server.DatagramServer;
import util.sendingUtils.Response;

import java.util.List;

public class Add extends Command{
    private static final long serialVersionUID = 1L;

    public Response execute(List<Object> params){
        PersonList personList = (PersonList) params.get(0);

        personList.addPerson((Person) params.get(1));
        DatagramServer.logger.info("Command Add completed");
        return new Response("Success","Person added");
    }

}
