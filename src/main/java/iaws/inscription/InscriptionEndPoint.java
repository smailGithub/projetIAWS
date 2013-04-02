package iaws.inscription;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

@Endpoint
public class InscriptionEndPoint {

	private static final String NAMESPACE_URI = "http://IAWS/";

	private XPath lastNameExpression;
	private XPath firstNameExpression;
	private XPath mailExpression;
	private XPath adrPostaleExpression;

	private InscriptionService inscriptionService;

	@Autowired
	public InscriptionEndPoint(InscriptionService inscriptionService) throws JDOMException {
		this.inscriptionService = inscriptionService;

		Namespace namespace = Namespace.getNamespace("iaws", NAMESPACE_URI);

		lastNameExpression = XPath.newInstance("//iaws:InscriptionRequest/nom");
		lastNameExpression.addNamespace(namespace);

		firstNameExpression = XPath.newInstance("//iaws:InscriptionRequest/prenom");
		firstNameExpression.addNamespace(namespace);

		mailExpression = XPath.newInstance("//iaws:InscriptionRequest/mail");
		mailExpression.addNamespace(namespace);

		adrPostaleExpression = XPath.newInstance("//iaws:InscriptionRequest/adrPostale");
		adrPostaleExpression.addNamespace(namespace);

	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "InscriptionRequest")
	public void handleInscriptionRequest(@RequestPayload Element inscriptionRequest) throws Exception {
		String lastname = lastNameExpression.valueOf(inscriptionRequest);
		String firstname = firstNameExpression.valueOf(inscriptionRequest);
		String mail = mailExpression.valueOf(inscriptionRequest);
		String adrPostale = adrPostaleExpression.valueOf(inscriptionRequest);

		inscriptionService.inscription(lastname, firstname, mail, adrPostale);
	}

}
