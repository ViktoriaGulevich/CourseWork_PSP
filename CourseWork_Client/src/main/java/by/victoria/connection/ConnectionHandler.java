package by.victoria.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionHandler {
    private static ConnectionHandler connectionHandler;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private ConnectionHandler() {
        try {
            socket = new Socket("127.0.0.1", 2525);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
        }
    }

    public static ConnectionHandler getConnectionHandler() {
        if (connectionHandler == null) {
            connectionHandler = new ConnectionHandler();
        }
        return connectionHandler;
    }

    public void writeObject(Object object) {
        try {
            outputStream.writeObject(object);
        } catch (IOException e) {
        }
    }

    public Object readObject() {
        Object object = null;
        try {
            object = inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return object;
    }


    public void close() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
        }
    }
}
