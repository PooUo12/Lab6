package commands;


import server.DatagramServer;
import util.Response;

import java.util.List;

import static parser.CommandsInfoList.*;
import static parser.CommandsInfoList.FILTER_STARTS_WITH_PASSPORT_ID_INFO;

public class Help extends Command{
    public Response execute(List<Object> params){
        String message = "Available commands: \n" + HELP_INFO + INFO_INFO + SHOW_INFO + ADD_INFO + UPDATE_INFO + REMOVE_BY_ID_INFO+ CLEAR_INFO + EXECUTE_SCRIPT_INFO+ EXIT_INFO+ ADD_IF_MAX_INFO+ ADD_IF_MIN_INFO+ REMOVE_LOWER_INFO+ REMOVE_ALL_BY_WEIGHT_INFO+ COUNT_GREATER_THAN_HEIGHT_INFO+ FILTER_STARTS_WITH_PASSPORT_ID_INFO;
        DatagramServer.logger.info("Command Help completed");
        return new Response("Success",message);
    }
}
