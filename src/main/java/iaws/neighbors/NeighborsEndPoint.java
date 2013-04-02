package iaws.neighbors;

import iaws.neighbors.NeighborsService;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

@Endpoint
public class NeighborsEndPoint {
	private static final String NAMESPACE_URI = "http://IAWS/";

	private XPath mailExpression;
	private XPath rayonExpression;

	private NeighborsService osmService;

	@Autowired
	public NeighborsEndPoint(NeighborsService osmService) throws JDOMException {
		this.osmService = osmService;

		Namespace namespace = Namespace.getNamespace("iaws", NAMESPACE_URI);

		mailExpression = XPath.newInstance("//iaws:VoisinRequest/mail");
		mailExpression.addNamespace(namespace);

		rayonExpression = XPath.newInstance("//iaws:VoisinRequest/rayon");
		rayonExpression.addNamespace(namespace);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "VoisinRequest")
	public void handleVoisinRequest(@RequestPayload Element voisinRequest) throws Exception {
		String mail = mailExpression.valueOf(voisinRequest);
		String rayon = rayonExpression.valueOf(voisinRequest);

		osmService.getVoisin(mail, rayon);
	}

}
