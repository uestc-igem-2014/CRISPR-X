package me.trifunovic.spaceassault.game.level;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LevelHandler extends DefaultHandler {

	Boolean currentElement = false;
	String currentValue = null;
	public static WaveList wavesList = null;
	Wave temp;

	public static WaveList getSitesList() {
		return wavesList;
	}

	public static void setSitesList(WaveList sitesList) {
		LevelHandler.wavesList = sitesList;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		if (localName.equals("space_assault"))
		{
			wavesList = new WaveList();
		} else if (localName.equals("wave")) {
			temp = new Wave();
		} else if (localName.equals("enemy")) {
			String attr = attributes.getValue("type");
			temp.setType(attr);
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;

		if (localName.equalsIgnoreCase("enemy"))
			temp.setCount(Integer.parseInt(currentValue));
		if (localName.equalsIgnoreCase("wave"))
			wavesList.addWave(temp);

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = new String(ch, start, length);
			currentElement = false;
		}

	}

}
