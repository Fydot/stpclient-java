package com.zhihu.stp;

import java.util.ArrayList;

public class Request {

    private ArrayList<String> args = new ArrayList<String>();

    public Request() {
    }

    public Request(ArrayList<String> args) {
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

    public ArrayList<String> getArgs() {
        return this.args;
    }
}
