package org.my.service.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class MasterDataServiceImplTest {

	@InjectMocks
	private MasterDataServiceImpl target;
	
	@Mock
	private RestTemplate restTemplate;
	
	@Test
	public void testGetCities() throws IOException {
		ArgumentCaptor<String> url = ArgumentCaptor.forClass(String.class); 
		ArgumentCaptor<HttpMethod> method = ArgumentCaptor.forClass(HttpMethod.class);
		ArgumentCaptor<RequestCallback> requestCallBack = ArgumentCaptor.forClass(RequestCallback.class);
		ArgumentCaptor<ResponseExtractor> extractor = ArgumentCaptor.forClass(ResponseExtractor.class);
		target.getCities();
		Mockito.verify(restTemplate).execute(url.capture(), method.capture(), requestCallBack.capture(), extractor.capture());
		assertEquals(null, url.getValue());
		assertEquals(HttpMethod.GET, method.getValue());
		assertEquals(null, requestCallBack.getValue());
	}
}
