package util.commands;

import util.sendingUtils.Response;
import server.DatagramServer;
import server.list.PersonList;

import java.util.List;

public class CountGreaterThanHeight extends Command {

    public Response execute(List<Object> params){
        int i;

        PersonList personList = (PersonList) params.get(0);
        int arg = (int) params.get(1);

        i = (int) personList.getList().stream().
                filter(s -> s.getHeight() > arg).count();
        DatagramServer.logger.info("Command CountGreaterThanHeight completed");
        return new Response("Success","Amount of elements : " + i);

    }
}
