package com.cornello.prototype.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HttpClientProperties {
	
	@Value("${rest.config.connectionTimeout}")
	private Integer connectionTimeout;
	
	@Value("${rest.config.requestTimeout}")
	private Integer requestTimeout;
	
	@Value("${rest.config.socketTimeout}")
	private Integer socketTimeout;
	
	@Value("${rest.config.maxConnection}")
	private Integer maxTotalConnections;
	
	@Value("${rest.config.maxRoute}")
	private Integer maxPerRoute;

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public Integer getRequestTimeout() {
		return requestTimeout;
	}

	public Integer getSocketTimeout() {
		return socketTimeout;
	}

	public Integer getMaxTotalConnections() {
		return maxTotalConnections;
	}

	public Integer getMaxPerRoute() {
		return maxPerRoute;
	}
	
}
