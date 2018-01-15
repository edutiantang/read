package wang.condon.read.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpUtil {

    private static final int connectTimeoutMillis = 20000;
    private static final int readTimeoutMillis = 20000;

    private HttpUtil() {
        // Nothing here
    }

    public static String get(String url, Map<String, String> requestHeader) throws Exception {

        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            setConnection(connection, "GET");
            connection.setRequestProperty("contentType", "UTF-8");
            addRequestHeader(connection, requestHeader);
            getResponseBody(connection, response);

        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response.toString();
    }

    public static String post(String url, Map<String, String> requestHeader, String requestBody) throws Exception {

        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            setConnection(connection, "POST");
            addRequestHeader(connection, requestHeader);
            addRequestBody(requestBody, connection);
            getResponseBody(connection, response);

        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response.toString();
    }

    public static String put(String url, Map<String, String> requestHeader, String requestBody) throws Exception {

        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            setConnection(connection, "PUT");
            addRequestHeader(connection, requestHeader);
            addRequestBody(requestBody, connection);
            getResponseBody(connection, response);

        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response.toString();
    }

    private static void setConnection(HttpURLConnection connection, String method) throws ProtocolException {
        connection.setRequestMethod(method);
        connection.setConnectTimeout(connectTimeoutMillis);
        connection.setReadTimeout(readTimeoutMillis);
        connection.setDoInput(true);
        connection.setDoOutput(true);
    }


    private static void addRequestHeader(HttpURLConnection connection, Map<String, String> requestHeader) {
        if (requestHeader != null && !requestHeader.isEmpty()) {
            Set<Map.Entry<String, String>> setHeader = requestHeader
                    .entrySet();
            for (Iterator<Map.Entry<String, String>> it = setHeader.iterator(); it
                    .hasNext(); ) {
                Map.Entry<String, String> entry = it.next();
                connection.addRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    private static void addRequestBody(String requestBody, HttpURLConnection connection) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
        dataOutputStream.writeBytes(requestBody);
    }

    private static void getResponseBody(HttpURLConnection connection, StringBuilder response) throws IOException {
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
    }
}