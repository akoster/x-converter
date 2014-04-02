package xcon.pilot.xml;

import java.util.HashMap;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class MapParser {

	public static void main(String[] args) {

		XStream xstream = new XStream(new DomDriver());
		xstream.alias("map", Map.class);
		xstream.addImplicitCollection(Map.class, "parents");
		xstream.alias("parent", Parent.class);
		xstream.useAttributeFor(Parent.class, "key");
		xstream.alias("child", Child.class);
		xstream.useAttributeFor(Child.class, "key");

		Map map = (Map) xstream
				.fromXML("<map><parent key='p1'><child key='c1'><value>value1</value></child></parent><parent key='p2'><child key='c2'><value>value1</value></child></parent></map>");

		System.out.println(xstream.toXML(map));

		java.util.Map<String, String> result = new HashMap<String, String>();
		for (Parent parent : map.getParents()) {

			Child child = parent.getChild();
			String key = parent.getKey() + "." + child.getKey();
			result.put(key, child.getValue());
			System.out.println(key + "=" + child.getValue());
		}
	}
}