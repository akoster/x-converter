package xcon.stackoverflow;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * http://stackoverflow.com/questions/4744483/issues-with-writing-test-case-in-java/4746030#4746030
 */
public class ABC {

	private static final Logger LOG = Logger.getLogger(ABC.class);
	private static final String DEFAULT_FILENAME = "Element.xml";
	private String host;
	private String port;
	private String browser;
	private String url;
	private String fullurl;

	public class AbcFileAccessException extends Exception {
		private static final long serialVersionUID = 1L;

		public AbcFileAccessException(Exception e) {
			super(e);
		}
	}

	public ABC() throws AbcFileAccessException {
		this(DEFAULT_FILENAME);
	}

	public ABC(String fileName) throws AbcFileAccessException {
		File file = new File(fileName);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			process(db.parse(file));
		} catch (ParserConfigurationException e) {
			throw new AbcFileAccessException(e);
		} catch (SAXException e) {
			throw new AbcFileAccessException(e);
		} catch (IOException e) {
			throw new AbcFileAccessException(e);
		}
	}

	public ABC(Document document) {
		process(document);
	}

	public void process(Document document) {

		if (document == null) {
			throw new IllegalArgumentException("Document may not be null");
		}
		document.getDocumentElement().normalize();
		LOG.info("Root element " + document.getDocumentElement().getNodeName());
		NodeList nodeLst = document.getElementsByTagName("selenium");

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

				Element fstElmnt = (Element) fstNode;
				NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("name");
				Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
				NodeList fstNm = fstNmElmnt.getChildNodes();
				String name = ((Node) fstNm.item(0)).getNodeValue();

				NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("value");
				Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
				NodeList lstNm = lstNmElmnt.getChildNodes();

				String value = ((Node) lstNm.item(0)).getNodeValue();
				if (name.equals("host")) {
					host = value;
				}
				if (name.equals("port")) {
					port = value;
				}
				if (name.equals("browser")) {
					browser = value;
				}
				if (name.equals("url")) {
					url = value;
				}
				if (name.equals("fullurl")) {
					fullurl = value;
				}
			}
		}
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getBrowser() {
		return browser;
	}

	public String getUrl() {
		return url;
	}

	public String getFullurl() {
		return fullurl;
	}
}
