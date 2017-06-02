package by.htp.parcer;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import by.htp.parcer.entity.Family;

public class MainFamilySAX {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

		XMLReader reader = XMLReaderFactory.createXMLReader();
		FamilySAXHadler handler = new FamilySAXHadler();
		reader.setContentHandler(handler);
		reader.parse(new InputSource("resources/FamilyTree.xml"));

		List<Family> familyList = handler.getFamilyXML();

		for (Family familyEntity : familyList) {
			System.out.println(familyEntity.getFamilyID());
		}
	}

}
