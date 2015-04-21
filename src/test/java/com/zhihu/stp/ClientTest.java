package com.zhihu.stp;

import junit.framework.TestCase;

import java.io.IOException;

/**
 * Unit test for simple StpRequest.
 */
public class ClientTest extends TestCase {

    public ClientTest(String testName) {
        super(testName);
    }

    public void testClientCall() throws IOException {
        StpClient stpClient = new StpClient("localhost", 50001, 10000, 10000);
        StpRequest stpRequest = new StpRequest();
        stpRequest.append("add");
        stpRequest.append("1");
        stpRequest.append("2");
        StpResponse stpResponse = stpClient.call(stpRequest);
        assertEquals(stpResponse.getArgs(), stpRequest.getArgs());
    }
}