/**
 * 
 */
package com.happiestmind.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author klawand
 *
 *
 */
@Component
public class ElasticSearchInvoker {

	Logger logger = LoggerFactory.getLogger(ElasticSearchInvoker.class);

	@Autowired(required = true)
	private RestTemplate restTemplate;

	@Value("${elastic.search.url}")
	private String elasticSearchURL;

	/****
	 * 
	 * @param lines
	 * @return String Response
	 */
	public String post(String lines) {
		String creatRequest = null;
		long currentTimeMillis = System.currentTimeMillis();
		String format = String.format("%s/%s", elasticSearchURL, currentTimeMillis);
		logger.info("URL for elastic search:" + format);

		creatRequest = creatRequest(lines);

		logger.info("Request :" + creatRequest);

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		String answer = null;
		HttpEntity<String> entity = new HttpEntity<String>(creatRequest, headers);
		try {

			answer = restTemplate.postForObject(format, entity, String.class);
		} catch (RestClientException e) {
			throw new RuntimeException(e.getMessage());
		}
		logger.info("elsatic search response :" + answer);

		return answer;

	}

	/****
	 * 
	 * @param word
	 * @return String response
	 */
	public String findWord(String word) {
		String creatRequest = null;
		String format = String.format("%s/%s", elasticSearchURL, "_search?pretty=true");
		logger.info("URL for elastic search:" + format);

		creatRequest = createQueryRequest(word);

		logger.info("Request :" + creatRequest);

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		String answer = null;
		HttpEntity<String> entity = new HttpEntity<String>(creatRequest, headers);
		try {

			answer = restTemplate.postForObject(format, entity, String.class);
		} catch (RestClientException e) {
			throw new RuntimeException(e.getMessage());
		}
		logger.info("elsatic search response GET query :" + answer);

		return answer;
	}

	private String createQueryRequest(String word) {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{");
		stringBuilder.append('"');
		stringBuilder.append("query");
		stringBuilder.append('"');
		stringBuilder.append(":{");
		stringBuilder.append('"');
		stringBuilder.append("match");
		stringBuilder.append('"');
		stringBuilder.append(":{");
		stringBuilder.append('"');
		stringBuilder.append("name");
		stringBuilder.append('"');
		stringBuilder.append(":");
		stringBuilder.append('"');
		stringBuilder.append(word);
		stringBuilder.append('"');
		stringBuilder.append("}");
		stringBuilder.append("},");

		stringBuilder.append('"');
		stringBuilder.append("highlight");
		stringBuilder.append('"');
		stringBuilder.append(":{");
		stringBuilder.append('"');
		stringBuilder.append("fields");
		stringBuilder.append('"');
		stringBuilder.append(":{");
		stringBuilder.append('"');
		stringBuilder.append("name");
		stringBuilder.append('"');
		stringBuilder.append(":");
		stringBuilder.append("{");
		stringBuilder.append('"');
		stringBuilder.append("type");
		stringBuilder.append('"');
		stringBuilder.append(":");
		stringBuilder.append('"');
		stringBuilder.append("plain");
		stringBuilder.append('"');
		stringBuilder.append("}");
		stringBuilder.append("}");
		stringBuilder.append("}");
		stringBuilder.append("}");

		return stringBuilder.toString();
	}

	public String getElasticSearchURL() {
		return elasticSearchURL;
	}

	public void setElasticSearchURL(String elasticSearchURL) {
		this.elasticSearchURL = elasticSearchURL;
	}

	/**
	 * @param line
	 * @return
	 */
	private String creatRequest(String line) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{");
		stringBuilder.append('"');
		stringBuilder.append("name");
		stringBuilder.append('"');
		stringBuilder.append(":");
		stringBuilder.append('"');
		stringBuilder.append(line);
		stringBuilder.append('"');
		stringBuilder.append("}");

		return stringBuilder.toString();
	}

}
