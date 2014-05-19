package loon.action.map.tmx;

import java.util.ArrayList;

import loon.utils.xml.XMLElement;


public class TMXTile {

	public int index;

	public String name;

	public String type;

	public int x;

	public int y;

	public int width;

	public int height;

	String image;

	public TMXProperty props;

	public TMXTile(XMLElement element) throws RuntimeException {
		name = element.getAttribute("name", "");
		type = element.getAttribute("type", "");
		x = element.getIntAttribute("x", 0);
		y = element.getIntAttribute("y", 0);
		String w = element.getAttribute("width", null);
		String h = element.getAttribute("height", null);
		width = Integer.parseInt(w == null || "".equals(w) ? "0" : w);
		height = Integer.parseInt(h == null || "".equals(h) ? "0" : h);
		XMLElement imageElement = element
				.getChildrenByName("image");
		if (imageElement != null) {
			image = imageElement.getAttribute("source", null);
		}
		XMLElement propsElement = element
				.getChildrenByName("properties");
		if (propsElement != null) {
			props = new TMXProperty();
			ArrayList<XMLElement> property = propsElement.list("property");
			for (int i = 0; i < property.size(); i++) {
				XMLElement propElement = property.get(i);
				String name = propElement.getAttribute("name", null);
				String value = propElement.getAttribute("value", null);
				props.setProperty(name, value);
			}

		}
	}

}
