package com.example.parkinglot.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequest {
    private final URL url;

    private Map<String, String> bodyData = null;

    private Map<String, String> headerData = null;

    private final HttpURLConnection connection;


    /**
     * Constructs a new HttpRequest object with the given URL.
     *
     * @param url the URL to connect to
     * @throws IOException if an I/O error occurs
     */
    public HttpRequest(URL url) throws IOException {
        this.url = url;
        connection = (HttpURLConnection) url.openConnection();
    }

    /**
     * Sets the header data for the request
     *
     * @param headerData A map of key-value pairs to be set as the header data
     */
    public void setHeader(Map<String, String> headerData) {
        // Assign the parameter to the class field
        this.headerData = headerData;
    }

    /**
     *
     *   Returns a Map of the body data associated with this request.
     *
     *   @return A Map of the body data associated with this request.
     */
    public Map<String, String> getBodyData() {
        return this.bodyData;
    }

    /**
     * Sets the body data for the request
     *
     * @param bodyData A map of key-value pairs to be set as the body data
     */
    public void setBodyData(Map<String, String> bodyData) {
        this.bodyData = bodyData;
    }


    /**
     *
     *   Returns the header data of the request.
     *
     *   @return A {@link Map} containing the header data of the request.
     */
    public Map<String, String> getHeaderData() {
        return this.headerData;
    }

    /**
     * Sends a request to the server and calls the appropriate callback depending on the response code.
     *
     * @param successCallBack The callback to be called if the response code is 200.
     * @param failCallBack    The callback to be called if the response code is not 200 and other errors.
     */
    public void request(SuccessCallBack successCallBack, FailCallBack failCallBack) {
        new Thread(() -> {
            try {
                connection.setDoInput(true);
                if (headerData != null)
                    for (String key : headerData.keySet())
                        connection.setRequestProperty(key, headerData.get(key));

                if (bodyData != null) {
                    connection.setDoOutput(true);
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    StringBuilder body = new StringBuilder();
                    for (String key : bodyData.keySet())
                        body.append(key).append("=").append(URLEncoder.encode(bodyData.get(key), "UTF-8")).append("&");
                    outputStream.writeBytes(body.toString());
                    outputStream.flush();
                    outputStream.close();
                }

                if (connection.getResponseCode() == 200) {
                    successCallBack.callBack(connection.getResponseCode(), readeResponse(connection.getResponseCode()));
                }
                else {
                    failCallBack.callBack(connection.getResponseCode(), readeResponse(connection.getResponseCode()));
                }
            }
            catch (IOException ioException) {
                failCallBack.callBack(-1, ioException.toString());

            }
            finally {
                if (connection != null)
                    connection.disconnect();
            }
        }).start();
    }

    /**
     * Sets the request method for the connection.
     *
     * @param method The request method to use for the connection.
     * @throws ProtocolException If the method is not valid.
     */
    public void setRequestMethod(String method) throws ProtocolException {
        connection.setRequestMethod(method);
    }

    public interface SuccessCallBack {
        /**
         * Callback method for handling the response from a request
         *
         * @param httpCode The HTTP status code of the response
         * @param response The response body
         */
        void callBack(int httpCode, String response);
    }

    public interface FailCallBack {
        /**
         * Callback method for handling HTTP responses
         *
         * @param httpCode     The HTTP response code
         * @param errorMessage The error message associated with the response
         */
        void callBack(int httpCode, String errorMessage);
    }

    private String readeResponse(int httpCode) throws IOException {
        InputStream inputStream;
        if(httpCode == 200)
            inputStream = connection.getInputStream();
        else
            inputStream = connection.getErrorStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            response.append(line);
        return response.toString();
    }
}
