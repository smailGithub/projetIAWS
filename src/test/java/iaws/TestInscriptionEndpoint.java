package iaws;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ws.test.server.MockWebServiceClient;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.payload;

@ContextConfiguration("application-context.xml")
public class TestInscriptionEndpoint {
	
	public class TestIntegrationReleveNotesEndPoint {

	    @Autowired
	    private ApplicationContext applicationContext;

	    private MockWebServiceClient mockClient;

	    @Before
	    public void createClient() {
	        mockClient = MockWebServiceClient.createClient(applicationContext);
	    }

	    @Test
	    public void releveNotesEndpoint() throws Exception {
	        Source requestPayload = new StreamSource(new ClassPathResource("Personnel.xml").getInputStream() );
//	        Source responsePayload = new StreamSource(new ClassPathResource("ReleveNotes.xml").getInputStream());

//	        mockClient.sendRequest(withPayload(requestPayload)).
//	                andExpect(payload(responsePayload));
	        
	        mockClient.sendRequest(withPayload(requestPayload));
	    }
	}

}
