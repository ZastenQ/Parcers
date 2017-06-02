package by.htp.parcer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import by.htp.parcer.entity.Child;
import by.htp.parcer.entity.Family;
import by.htp.parcer.entity.Father;
import by.htp.parcer.entity.Mother;
import by.htp.parcer.entity.TagName;

public class MainFamilyStAX {

	public static void main(String[] args) throws FileNotFoundException {
		List<Family> familyList = new ArrayList<Family>();
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		try {
			InputStream input = new FileInputStream("resources/FamilyTree.xml");
			XMLStreamReader reader = inputFactory.createXMLStreamReader(input);
			familyList = process(reader);

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		for (Family familyEntity : familyList) {
			System.out.println(familyEntity.getFamilyID());
		}
	}

	private static List<Family> process(XMLStreamReader reader) throws XMLStreamException {
		List<Family> familyXML = new ArrayList<Family>();
		Family familyEntity = null;
		Mother familyMother = null;
		Father familyFather = null;
		Child familyChild = null;
		List<Child> familyChildren = null;
		TagName elementName = null;

		boolean inMother = false;
		boolean inFather = false;
		boolean inChild = false;

		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				elementName = TagName.getElementTagName(reader.getLocalName());

				switch (elementName) {
				case FAMILY:
					familyEntity = new Family();
					familyXML = new ArrayList<Family>();
					familyEntity.setFamilyID(Integer.parseInt(reader.getAttributeValue(null, "id")));
					break;
				case MOTHER:
					familyMother = new Mother();
					inMother = true;
					break;
				case FATHER:
					familyFather = new Father();
					inFather = true;
					break;
				case CHILDREN:
					familyChildren = new ArrayList<Child>();
					break;
				case CHILD:
					familyChild = new Child();
					inChild = true;
					familyChild.setGender(String.valueOf(reader.getAttributeValue(null, "gender")));
					familyChild.setDateBirth(String.valueOf(reader.getAttributeValue(null, "date-of-birth")));
					break;
				default:
					break;
				}
				break;
			case XMLStreamConstants.CHARACTERS:
				String text = reader.getText().trim();

				if (text.isEmpty()) {
					break;
				}

				switch (elementName) {

				case NAME:
					if (inMother) {
						familyMother.setName(text);
					} else if (inFather) {
						familyFather.setName(text);
					} else if (inChild) {
						familyChild.setName(text);
					}
					break;
				case SURNAME:
					if (inMother) {
						familyMother.setSurname(text.toString());
					} else if (inFather) {
						familyFather.setSurname(text.toString());
					} else if (inChild) {
						familyChild.setSurname(text.toString());
					}
					break;
				case MAIDEN_NAME:
					familyMother.setMaidenName(text);
					break;
				case AGE:
					if (inMother) {
						familyMother.setAge(Integer.parseInt(text));
					} else if (inFather) {
						familyFather.setAge(Integer.parseInt(text));
					}
					break;
				default:
					break;
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				elementName = TagName.getElementTagName(reader.getLocalName());
				switch (elementName) {
				case MOTHER:
					familyEntity.setFamilyMother(familyMother);
					inMother = false;
					break;
				case FATHER:
					familyEntity.setFamilyFather(familyFather);
					inFather = false;
					break;
				case CHILD:
					familyChildren.add(familyChild);
					inChild = false;
					break;
				case CHILDREN:
					familyEntity.setFamilyChildren(familyChildren);
					break;
				case FAMILY:
					familyXML.add(familyEntity);
					break;
				default:
					break;
				}

			}

		}

		return familyXML;
	}

}
