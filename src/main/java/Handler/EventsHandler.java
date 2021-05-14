package Handler;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import Service.Events;
import Request.EventsRequest;
import Result.EventsResult;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.sql.Connection;

public class EventsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {

                Headers reqHeaders = exchange.getRequestHeaders();

                if (reqHeaders.containsKey("Authorization")) {
                    EventsRequest request = new EventsRequest();
                    request.setAuthToken(reqHeaders.getFirst("Authorization"));
                    String authToken = request.getAuthToken();
                    String uri = exchange.getRequestURI().toString();


                    AuthToken temp = findToken(authToken);
                    if (temp != null) {
                        uri = uri.substring(1);
                        if (uri.contains("/")) {
                            int index = uri.indexOf("/");
                            request.setEventID(uri.substring(index + 1));

                        }

                        EventsResult result = new EventsResult();
                        Events eventService = new Events();
                        result = eventService.event(request);


                        if (result.getSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }


                        String respData = gson.toJson(result);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(respData, respBody);
                        respBody.close();

                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        EventsResult result = new EventsResult();
                        result.setMessage("Error: Unauthorized");
                        result.setSuccess(false);
                        String respData = gson.toJson(result);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(respData, respBody);
                        respBody.close();
                    }
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
                }
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
            exchange.getResponseBody().close();
        } catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }

    public AuthToken findToken(String token) throws DataAccessException {
        Database database = new Database();
        Connection conn = database.openConnection();

        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        AuthToken temp = authTokenDao.findFromToken(token);
        database.closeConnection(true);

        return temp;
    }
}
