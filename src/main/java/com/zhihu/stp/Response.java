package com.zhihu.stp;

import java.util.Vector;

/**
 * Created by hdd on 4/14/15.
 */

public class Response<T> {

    private Vector<String> args = new Vector<String>();

    public Response() {
    }

    public Response(Vector<String> args) {
        this.args = args;
    }

    public void append(String arg) {
        this.args.add(arg);
    }

    public Vector<String> getArgs() {
        return this.args;
    }

    public String toString() {
        String str = "";

        for (String elem: this.args) {
            str += elem + " ";
        }

        return str;
    }

}
