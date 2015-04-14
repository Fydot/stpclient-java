package com.zhihu.stp;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by hdd on 4/14/15.
 */

public class Client {

    private String host;
    private int port, connectTimeout, timeout;
    private SocketAddress socketAddress;
    private Socket socket = new Socket();
    private BufferedReader reader;
    private BufferedWriter writer;

    public Client(String host, int port) {
        this.init(host, port, 1000, 1000);
    }

    public Client(String host, int port, int connectTimeout) {
        this.init(host, port, connectTimeout, 1);
    }

    public Client(String host, int port, int connectTimeout, int timeout) {
        this.init(host, port, connectTimeout, timeout);
    }

    private void init(String host, int port, int connectTimeout, int timeout) {
        this.host = host;
        this.port = port;
        this.connectTimeout = connectTimeout;
        this.timeout = timeout;
        this.socketAddress = new InetSocketAddress(this.host, this.port);

    }

    public void connect() throws IOException {
        this.socket.connect(this.socketAddress, this.connectTimeout);
        this.socket.setSoTimeout(this.timeout);
        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }

    private Response receive() throws IOException {
        String buf = "", temp;
        Response response = new Response();
        while(true) {
            // Receive Length
            while (!buf.contains("\r\n")) {
                temp = this.reader.readLine();
                buf += temp + "\r\n";
            }

            if(buf.startsWith("\r\n")) {
                buf = buf.substring("\r\n".length());
                return response;
            }

            int length = Integer.parseInt(buf.substring(0, buf.indexOf("\r\n")));
            buf = buf.substring(buf.indexOf("\r\n") + "\r\n".length());

            while (buf.length() <= length + "\r\n".length()) {
                temp = this.reader.readLine();
                buf += temp + "\r\n";
            }

            String data = buf.substring(0, length);
            buf = buf.substring(length + "\r\n".length());

            response.append(data);
        }
    }

    public Response call(Request request) throws IOException {

        if (!this.socket.isConnected()) {
            try {
                this.connect();
            } catch (IOException e) {
                System.err.println("Connect " + e);
            }
        }

        this.writer.write(request.serialize());
        this.writer.flush();

        return this.receive();
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 50001);
        Request requset = new Request();
        requset.append("hdd");
        requset.append("hdd2cdcdcdcdcd");
        try {
            System.out.println("INFO " + client.call(requset));
            //client.call(requset);
        } catch (IOException e) {
            System.err.println("ERROR ERROR -- " + e);
        }
    }
}