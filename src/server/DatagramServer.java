package server;

import creator.PersonCreator;
import exceptions.IllegalFieldsException;
import list.ListSaver;
import list.PersonList;
import parser.Parser;
import sun.misc.Signal;
import util.Port;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;

public class DatagramServer {
    public static final Logger logger = Logger.getLogger(DatagramServer.class.getSimpleName());
    PersonList personList;

    public DatagramChannel startServer() {
        DatagramChannel server = null;
        InetSocketAddress address = new InetSocketAddress("localhost", Port.port);
        try {
            server = DatagramChannelBuilder.bindChannel(address);
            server.configureBlocking(false);
            logger.info("Bound successfully");
        } catch (IOException e) {
            logger.info("Binding error");
            System.exit(-1);
        }
        personList = new PersonList();
        PersonCreator personCreator = new PersonCreator(personList);
        Parser parser = new Parser();

        try {
            parser.parser(personCreator, personList);
            logger.info("Collection from file saved");
        } catch (IllegalFieldsException e) {
            logger.info("csv-file is damaged collection can't be saved");
        } catch (IOException e) {
            e.printStackTrace();
        }

        setupShutDownWork(personList,server);
        setupSignalHandler(personList,server);

        logger.info("Server started at #" + address);
        return server;
    }


    private void setupSignalHandler(PersonList personList, DatagramChannel channel) {
        Signal.handle(new Signal("TSTP"), signal -> {
            ListSaver listSaver = new ListSaver(personList.getList());
            listSaver.save();
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setupShutDownWork(PersonList personList, DatagramChannel channel) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ListSaver listSaver = new ListSaver(personList.getList());
            listSaver.save();
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    public static void main(String[] args) {
        DatagramServer server = new DatagramServer();
        DatagramChannel channel = server.startServer();
        MessageProcessor messageProcessor = new MessageProcessor(server.personList);
        while (true) {
            try {
                messageProcessor.receiveMessage(channel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
