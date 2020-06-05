package com.front.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "orkestr")
public class FrontConfiguration {
	private String url;
	private String basePath;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public String toString() {
		return "StorageConfiguration [url=" + url + ", basePath=" + basePath + "]";
	}
	
}
