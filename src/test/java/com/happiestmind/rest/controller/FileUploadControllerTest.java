/**
 * 
 */
package com.happiestmind.rest.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.swing.Spring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockRequestDispatcher;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.happiestmind.api.util.ElasticSearchInvoker;

/**
 * @author klawand
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileUploadController.class)
@AutoConfigureMockMvc
public class FileUploadControllerTest {

	@InjectMocks
	private FileUploadController fileUploadController;

	@Mock
	private ElasticSearchInvoker elasticSearchInvoker;
	
	@Autowired
    private MockMvc mockMvc;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindWord() throws Exception {
		Mockito.when(elasticSearchInvoker.findWord(Mockito.anyString())).thenReturn("{\\r\\n\\t\\\"highlight\\\": {\\r\\n\\t\\t\\\"name\\\": [\\r\\n\\t\\t\\t\\\" something. <em>for</em> why pure we Match caps wasnt and of just or other. will was Cup, We awards, Series scored Every he\\\"\\r\\n\\t\\t]\\r\\n\\t}\\r\\n}");
		
		ResultActions andExpect = mockMvc.perform(MockMvcRequestBuilders.post("/find").content("").accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());

		
	}

}
