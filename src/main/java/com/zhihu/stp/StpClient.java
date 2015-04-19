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
    private String EOL = "\r\n";

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
        StringBuilder buf = new StringBuilder();
        StpResponse stpResponse = new StpResponse();
        char[] cbuf = new char[1500];
        int cbufLength;
        while (true) {
            // Receive Length
            while (buf.indexOf(this.EOL) == -1) {
                cbufLength = this.reader.read(cbuf, 0, cbuf.length);
                buf.append(cbuf, 0, cbufLength);
            }

            if (buf.indexOf(this.EOL) == 0) {
                buf.delete(0, this.EOL.length());
                return stpResponse;
            }

            int length = Integer.parseInt(buf.substring(0, buf.indexOf(this.EOL)));
            buf = buf.delete(0, buf.indexOf(this.EOL) + this.EOL.length());

            while (buf.length() <= length + this.EOL.length()) {
                cbufLength = this.reader.read(cbuf, 0, cbuf.length);
                buf.append(cbuf, 0, cbufLength);
            }

            String data = buf.substring(0, length);
            buf = buf.delete(0, length + this.EOL.length());

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