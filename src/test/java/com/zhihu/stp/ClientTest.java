package com.zhihu.stp;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;

/**
 * Unit test for simple Request.
 */
public class ClientTest extends TestCase {

    public ClientTest(String testName) {
        super(testName);
    }

    public void testClientCall() throws IOException {
        Client client = new Client("localhost", 50001, 10000, 10000);
        Request request = new Request();
        request.append("add");
        request.append("1");
        request.append("2");
        Response response = client.call(request);

        assertEquals(response.getArgs(), request.getArgs());
    }
}
