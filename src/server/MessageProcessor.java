package server;

import commands.Command;
import list.PersonList;
import util.Port;
import util.Request;
import util.Response;
import util.Serializer;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MessageProcessor {
    List<Object> args = null;
    PersonList personList;

    public MessageProcessor(PersonList personList){
        this.personList = personList;
    }

    public void receiveMessage(DatagramChannel server) throws IOException {
        SocketAddress remoteAdd = null;
        ByteBuffer buffer = ByteBuffer.allocate(Port.size);
        while (remoteAdd == null){
            try {
                remoteAdd = server.receive(buffer);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Response response = extractMessage(buffer);
        ByteBuffer answer = ByteBuffer.wrap(Serializer.serializer(response).toByteArray());
        sendMessage(answer, remoteAdd, server);
    }

    private Response extractMessage(ByteBuffer buffer) {
        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        Request request = (Request) Serializer.deserializer(buffer.array());
        Command command = request.getCommand();
        args = request.getArgs();
        if (args == null){
            args = new ArrayList<>();
            args.add(personList);
        } else {
            args.add(0, personList);
        }
        DatagramServer.logger.info("Client sent: " + request.getCommand().toString());
        Response response = command.execute(args);
        return response;
    }

    private void sendMessage(ByteBuffer buffer, SocketAddress serverAddress, DatagramChannel server) {
        try {
            DatagramServer.logger.info("Sending response...");
            Instant deadline = Instant.now().plusSeconds(1);
            while (Instant.now().isBefore(deadline)) {
                buffer.rewind();
                int numSent = server.send(buffer, serverAddress);
                if (numSent > 0) break;
                Thread.sleep(100);
            }
            DatagramServer.logger.info("Response was successfully sent");
        } catch (IOException | InterruptedException e) {
            DatagramServer.logger.info("Error sending packet to client");
        }
    }
}
