package Handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String urlPath = exchange.getRequestURI().toString();
        if ((urlPath.equals("/")) || (urlPath == null)) {
            urlPath = "/index.html";
        }
        String filePath = "web" + urlPath;
        File file = new File(filePath);
        if(!file.exists()) {
            //return 404 error and send custom 404 page
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            OutputStream errorResponse = exchange.getResponseBody();
            File error = new File("web" + "/HTML/404.html");
            Files.copy(error.toPath(),errorResponse);
            errorResponse.close();
        }
        else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            Files.copy(file.toPath(), respBody);
            respBody.close();
        }
    }
}
