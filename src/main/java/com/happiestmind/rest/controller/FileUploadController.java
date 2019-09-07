/**
 * 
 */
package com.happiestmind.rest.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.happiestmind.api.util.ElasticSearchInvoker;
import com.happiestmind.api.util.FindWordRequest;

/**
 * @author klawand
 *
 */
@RestController
public class FileUploadController {

	private static final String RESULT_MESSAGE = "File Upload Successfully";
	private static final String RESULT_MESSAGE1 = "Please enter valid word";

	@Autowired
	ElasticSearchInvoker elasticSearchInvoker;

	Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	/****
	 * <p>
	 * This is POST API call to Upload file stored its unique word into elastic
	 * search
	 * </p>
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/file/upload", method = RequestMethod.POST)
	public ResponseEntity<String> upload(@RequestParam MultipartFile file) throws IOException {

		StringBuilder stringBuilder = new StringBuilder();

		String contentType = file.getContentType();

		String originalFilename = file.getOriginalFilename();

		logger.info("Content Type:" + contentType);
		logger.info("File name:" + originalFilename);

		InputStream inputStream = file.getInputStream();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		String readLine;
		while ((readLine = bufferedReader.readLine()) != null) {
			if (!readLine.isEmpty()) {
				stringBuilder.append(readLine);
				stringBuilder.append(" ");
			}
		}

		String[] split = stringBuilder.toString().split(" ");
		Set<String> set = new HashSet<String>(Arrays.asList(split));
		String join = String.join(" ", set);

		elasticSearchInvoker.post(join);

		return new ResponseEntity<String>(RESULT_MESSAGE, HttpStatus.OK);
	}

	/**
	 * <p>
	 * This is POST API for checking enter word is exist in Elastic search or not
	 * </p>
	 * 
	 * @param query
	 * @return String Response
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> findWord(@RequestBody FindWordRequest findWordRequest) {
		// check word is empty or not

		if (findWordRequest.getName() == null || findWordRequest.getName().isEmpty()) {
			return new ResponseEntity<String>(RESULT_MESSAGE1, HttpStatus.OK);
		}
		String findWord = elasticSearchInvoker.findWord(findWordRequest.getName());
		return new ResponseEntity<String>(findWord, HttpStatus.OK);
	}

}
