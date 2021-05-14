package Handler;

import DataAccess.DataAccessException;
import Service.Fill;
import Request.FillRequest;
import Request.RegisterRequest;
import Result.FillResult;
import Result.RegisterResult;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                Fill service = new Fill();
                FillResult result = new FillResult();
                FillRequest request = new FillRequest();

                String uri = exchange.getRequestURI().toString();
                uri = uri.substring(6);
                if (uri.contains("/")) {
                    int index = uri.indexOf("/");
                    request.setUsername(uri.substring(0, index));
                    request.setGenerations(Integer.parseInt(uri.substring(index + 1)));

                }
                else {
                    request.setUsername(uri);
                    request.setGenerations(4);
                }
                result = service.fill(request);

                if (result.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                }

                String response = gson.toJson(result);
                OutputStream responseBody = exchange.getResponseBody();
                writeString(response, responseBody);
                responseBody.close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
            exchange.getResponseBody().close();
        }
        catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
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
}
