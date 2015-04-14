package com.zhihu.stp;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


public class StpClient {

    private String host;
    private int port, connectTimeout, timeout;
    private SocketAddress socketAddress;
    private Socket socket = new Socket();
    private BufferedReader reader;
    private BufferedWriter writer;

    public StpClient(String host, int port) {
        this.init(host, port, 1000, 1000);
    }

    public StpClient(String host, int port, int connectTimeout, int timeout) {
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

    public void close() throws IOException {
        this.reader.close();
        this.writer.close();
        if (!this.socket.isClosed()) {
            this.socket.close();
        }
    }

    public boolean available() {
        return this.socket.isConnected();
    }

    private StpResponse receive() throws IOException {
        String buf = "", temp;
        StpResponse stpResponse = new StpResponse();
        while(true) {
            // Receive Length
            while (!buf.contains("\r\n")) {
                temp = this.reader.readLine();
                buf += temp + "\r\n";
            }

            if(buf.startsWith("\r\n")) {
                buf = buf.substring("\r\n".length());
                return stpResponse;
            }

            int length = Integer.parseInt(buf.substring(0, buf.indexOf("\r\n")));
            buf = buf.substring(buf.indexOf("\r\n") + "\r\n".length());

            while (buf.length() <= length + "\r\n".length()) {
                temp = this.reader.readLine();
                buf += temp + "\r\n";
            }

            String data = buf.substring(0, length);
            buf = buf.substring(length + "\r\n".length());

            stpResponse.append(data);
        }
    }

    public StpResponse call(StpRequest stpRequest) throws IOException {

        if (!this.socket.isConnected()) {
            try {
                this.connect();
            } catch (IOException e) {
                System.err.println("Connect " + e);
            }
        }

        this.writer.write(stpRequest.serialize());
        this.writer.flush();

        return this.receive();
    }
}