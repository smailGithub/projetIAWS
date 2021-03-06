package iaws;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.server.MockWebServiceClient;

import static org.springframework.ws.test.server.RequestCreators.withPayload;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("application-context.xml")
public class TestInscriptionEndPoint {

	@Autowired
	private ApplicationContext applicationContext;

	private MockWebServiceClient mockClient;

	@Before
	public void createClient() {
		mockClient = MockWebServiceClient.createClient(applicationContext);
	}

	@Test
	public void inscriptionEndPoint() throws Exception {
		Source requestPayload = new StreamSource(new ClassPathResource("UserExample.xml").getInputStream());

		mockClient.sendRequest(withPayload(requestPayload));
	}

}
