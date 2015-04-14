package com.zhihu.stp;

import java.util.Vector;

public class Request {

    private Vector<String> args = new Vector<String>();

    public Request() {
    }

    public Request(Vector<String> args) {
        this.args = args;
    }

    public void append(String arg) {
        this.args.add(arg);
    }

    public String serialize() {

        String buf = "";
        for (String elem : this.args) {
            buf += String.format("%d\r\n%s\r\n", elem.length(), elem);
        }
        buf += "\r\n";
        return buf;
    }

    public Vector<String> getArgs() {
        return this.args;
    }
}
