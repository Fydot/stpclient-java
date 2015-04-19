package com.zhihu.stp;

import java.util.ArrayList;

public class StpRequest {

    private ArrayList<String> args = new ArrayList<String>();

    public StpRequest() {
    }

    public StpRequest(ArrayList<String> args) {
        this.args = args;
    }

    public void append(String arg) {
        this.args.add(arg);
    }

    public String serialize() {

        StringBuffer buf = new StringBuffer();
        for (String elem : this.args) {
            buf.append(elem.length() + "\r\n" + elem.toString() + "\r\n");
        }
        buf.append("\r\n");
        return buf.toString();
    }

    public ArrayList<String> getArgs() {
        return this.args;
    }
}
