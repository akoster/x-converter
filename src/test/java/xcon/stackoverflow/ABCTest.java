package xcon.stackoverflow;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import xcon.stackoverflow.ABC;

public class ABCTest {

	private Document document;

	@Before
	public void setUp() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.newDocument();
	}

	@Test
	public void testProcess() throws Exception {

		Element root = (Element) document.createElement("root");
		document.appendChild(root);

		String host = "myhost";
		String port = "myport";
		String browser = "mybrowser";
		String url = "myurl";
		String fullurl = "myfullurl";

		root.appendChild(createElement("host", host));
		root.appendChild(createElement("port", port));
		root.appendChild(createElement("browser", browser));
		root.appendChild(createElement("url", url));
		root.appendChild(createElement("fullurl", fullurl));

		// for your convenience
		printXml();

		ABC instance = new ABC(document);

		Assert.assertEquals(host, instance.getHost());
		Assert.assertEquals(port, instance.getPort());
		Assert.assertEquals(browser, instance.getBrowser());
		Assert.assertEquals(url, instance.getUrl());
		Assert.assertEquals(fullurl, instance.getFullurl());
	}

	private Element createElement(String name, String value) {

		Element result = (Element) document.createElement("selenium");

		Element nameElement = document.createElement("name");
		nameElement.setTextContent(name);
		result.appendChild(nameElement);

		Element valueElement = document.createElement("value");
		valueElement.setTextContent(value);
		result.appendChild(valueElement);

		return result;

	}

	private void printXml() throws Exception {
		Transformer transformer;
		transformer = TransformerFactory.newInstance().newTransformer();
		Source source = new DOMSource(document);
		Result output = new StreamResult(System.out);
		transformer.transform(source, output);
	}
}
