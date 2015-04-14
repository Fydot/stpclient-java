stpclient-java
===

## Usage

	import com.zhihu.stp.StpClient
	import com.zhihu.stp.StpRequest
	import com.zhihu.stp.StpResponse
	
	StpClient client = new StpClient(host, port)
	// You can also, add connect and io-wait timeout
	StpClient client = new StpClient(host, port, connectTimeout, timeout)
	StpRequest stpRequest = new StpRequest();
	stpRequest.append(arg1)
	stpRequest.append(arg2)
	
	// You can use `StpRequest stpRequest = new StpRequest(Vector<String> xxx)` too
	
	StpResponse stpResponse = client.call(stpRequest)
	
	client.close()


## Reference

See also:

[stpclient-py](https://github.com/dccmx/stpclient-py)

[stpclient-go](https://github.com/lfyzjck/stpclient-go)