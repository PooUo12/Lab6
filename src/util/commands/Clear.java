package util.commands;

import server.list.PersonList;
import server.DatagramServer;
import util.sendingUtils.Response;

import java.util.List;


public class Clear extends Command {

    public Response execute(List<Object> params){
        PersonList personList = (PersonList) params.get(0);
        personList.listClean();
        DatagramServer.logger.info("Command Clear completed");
        return new Response("Success","List was cleaned");

    }
}
