package commands;

import list.PersonList;
import server.DatagramServer;
import util.Response;

import java.util.List;

public class CountGreaterThanHeight extends Command {

    public Response execute(List<Object> params){
        int i;

        PersonList personList = (PersonList) params.get(0);
        int arg = (int) params.get(1);

        i = (int) personList.getList().stream().
                filter(s -> s.getHeight() > arg).count();
        System.out.println(i);
        DatagramServer.logger.info("Command CountGreaterThanHeight completed");
        return new Response("Success","Amount of elements : " + i);

    }
}
