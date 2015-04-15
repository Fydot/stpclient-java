package com.zhihu.stp;

import java.util.ArrayList;

/**
 * Created by hdd on 4/14/15.
 */

public class StpResponse {

    private ArrayList<String> args = new ArrayList<String>();

    public StpResponse() {
    }

    public StpResponse(ArrayList<String> args) {
        this.args = args;
    }

    public void append(String arg) {
        this.args.add(arg);
    }

    public ArrayList<String> getArgs() {
        return this.args;
    }

    public String getArg(int index) {
        return this.args.get(index);
    }

    public int size() {
        return this.args.size();
    }
    public String toString() {
        String str = "";

        for (String elem : this.args) {
            str += elem + " ";
        }

        return str;
    }

}
