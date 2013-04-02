package iaws.osm;

import iaws.osm.OsmService;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

@Endpoint
public class OsmEndPoint {
	private static final String NAMESPACE_URI = "http://IAWS/";

	private XPath longitudeExpression;
	private XPath latitudeExpression;

	private OsmService osmService;

	@Autowired
	public OsmEndPoint(OsmService osmService) throws JDOMException {
		this.osmService = osmService;

		Namespace namespace = Namespace.getNamespace("iaws", NAMESPACE_URI);

		latitudeExpression = XPath.newInstance("//iaws:VoisinRequest/latitude");
		latitudeExpression.addNamespace(namespace);

		longitudeExpression = XPath.newInstance("//iaws:VoisinRequest/longitude");
		longitudeExpression.addNamespace(namespace);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "VoisinRequest")
	public void handleVoisinRequest(@RequestPayload Element voisinRequest) throws Exception {
		String latitude = latitudeExpression.valueOf(voisinRequest);
		String longitude = longitudeExpression.valueOf(voisinRequest);

		osmService.getVoisin(latitude, longitude);
	}

}
