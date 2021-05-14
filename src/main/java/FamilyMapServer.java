import java.net.InetSocketAddress;
import java.io.*;
import java.net.*;

import Handler.*;
import com.sun.net.httpserver.*;
import org.xml.sax.helpers.DefaultHandler;

public class FamilyMapServer {
    public static void main(String[] args) throws IOException {
        //int port = Integer.parseInt(args[0]);
        int port = 8080;
        InetSocketAddress serverAddress = new InetSocketAddress((int) port);
        HttpServer server = HttpServer.create(serverAddress, 10);

        server.createContext("/", new FileHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonsHandler());
        server.createContext("/event", new EventsHandler());

        server.start();
        System.out.println("FamilyMapServer listening on port " + port);
    }
}