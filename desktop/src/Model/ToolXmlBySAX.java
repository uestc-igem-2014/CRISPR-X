package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class ToolXmlBySAX {
	
	public static List<HashMap<String, String>> _readXml(InputStream input, String nodeName){
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser parser = spf.newSAXParser();
			AnalyzeSBOL handler = new AnalyzeSBOL(nodeName);
			parser.parse(input, handler);
			input.close();
			return handler.getList();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<HashMap<String, String>> jiexisbol(File sbol) {
		try {
			FileInputStream input = new FileInputStream(sbol);
			List<HashMap<String, String>> list = _readXml(input, "s:nucleotides");
			for(HashMap<String, String> p : list){
//				System.out.println(p.toString());
			}
			return list;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
