stpclient-java
===

## Usage

	import com.zhihu.stp.Client
	import com.zhihu.stp.Request
	import com.zhihu.stp.Response
	
	Client client = new Client(host, port)
	// You can also, add connect and io-wait timeout
	Client client = new Client(host, port, connectTimeout, timeout)
	Request request = new Request();
	request.append(arg1)
	request.append(arg2)
	
	// You can use `Request request = new Request(Vector<String> xxx)` too
	
	Response response = client.call(request)
	
	client.close()


## Reference

See also:

[stpclient-py](https://github.com/dccmx/stpclient-py)

[stpclient-go](https://github.com/lfyzjck/stpclient-go)