package client;

import creator.CreateFromConsoleParser;
import parser.CommandsParser;
import util.Port;
import util.Request;
import util.Response;
import util.Serializer;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Scanner;

public class Client {
    DatagramSocket ds;
    InetAddress host;
    DatagramPacket dp;
    int port = Port.port;

    public void client(byte[] arr){
        try {
            byte[] arr1 = Port.arr;
            ds = new DatagramSocket();
            host = InetAddress.getLocalHost();
            dp = new DatagramPacket(arr, arr.length,host,port);
            ds.send(dp);
            dp = new DatagramPacket(arr1, arr1.length );
            ds.receive(dp);
            Response response = (Response) Serializer.deserializer(dp.getData());
            System.out.println(response);
            ds.close();
        } catch (IOException e) {
            System.out.println("Object too big");
        }
    }

    public static void main(String[] args){
        Client client = new Client();
        Scanner in = new Scanner(System.in);
        CreateFromConsoleParser createFromConsoleParser = new CreateFromConsoleParser();
        CommandsParser commandsParser = new CommandsParser( createFromConsoleParser, in);
        do {
            List<Request> requests = commandsParser.parseCommand();
            if (requests != null) {
                for (Request request : requests) {
                    if (request == null) {
                        break;
                    }
                    client.client(Serializer.serializer(request).toByteArray());
                }
            } else{
                System.out.println("U stopped the program, so scripts execution was stopped");
            }
        } while (!commandsParser.getFlag());

        in.close();


    }
}
