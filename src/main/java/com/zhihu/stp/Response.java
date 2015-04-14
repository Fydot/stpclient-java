package com.zhihu.stp;

import java.util.ArrayList;

/**
 * Created by hdd on 4/14/15.
 */

public class Response<T> {

    private ArrayList<String> args = new ArrayList<String>();

    public Response() {
    }

    public Response(ArrayList<String> args) {
        this.args = args;
    }

    public void append(String arg) {
        this.args.add(arg);
    }

    public ArrayList<String> getArgs() {
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
