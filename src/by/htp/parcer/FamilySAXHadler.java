package by.htp.parcer;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import by.htp.parcer.entity.Child;
import by.htp.parcer.entity.Family;
import by.htp.parcer.entity.Father;
import by.htp.parcer.entity.Mother;
import by.htp.parcer.entity.TagName;

public class FamilySAXHadler extends DefaultHandler {
	private List<Family> familyXML;
	private Family familyEntity;
	private Mother familyMother;
	private Father familyFather;
	private Child familyChild;
	private List<Child> familyChildren;
	private StringBuilder text;
	private boolean inMother = false;
	private boolean inFather = false;
	private boolean inChild = false;

	public List<Family> getFamilyXML() {
		return familyXML;
	}

	public void startDocument() throws SAXException {
		System.out.println("Parsing started");
	}

	public void endDocument() throws SAXException {
		System.out.println("Parsing ended");
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// System.out.println("startElement -> " + uri + ", localName: " +
		// localName + ", qName: " + qName);

		text = new StringBuilder();
		if (qName.equals("family")) {
			familyEntity = new Family();
			familyXML = new ArrayList<Family>();
			familyEntity.setFamilyID(Integer.parseInt(attributes.getValue("id")));
		} else if (qName.equals("mother")) {
			familyMother = new Mother();
			inMother = true;
		} else if (qName.equals("father")) {
			familyFather = new Father();
			inFather = true;
		} else if (qName.equals("children")) {
			familyChildren = new ArrayList<Child>();
		} else if (qName.equals("child")) {
			familyChild = new Child();
			inChild = true;
			familyChild.setGender(String.valueOf(attributes.getValue("gender")));
			familyChild.setDateBirth(String.valueOf(attributes.getValue("date-of-birth")));
		}
	}

	public void characters(char[] buffer, int start, int lenght) {
		text.append(buffer, start, lenght);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		TagName familyTagName = TagName.valueOf(qName.toUpperCase().replace("-", "_"));

		switch (familyTagName) {
		case NAME:
			if (inMother) {
				familyMother.setName(text.toString());
			} else if (inFather) {
				familyFather.setName(text.toString());
			} else if (inChild) {
				familyChild.setName(text.toString());
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
			familyMother.setMaidenName(text.toString());
			break;
		case AGE:
			if (inMother) {
				familyMother.setAge(Integer.parseInt(text.toString()));
			} else if (inFather) {
				familyFather.setAge(Integer.parseInt(text.toString()));
			}
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

	public void warning(SAXParseException e) {
		System.err.println("WARNING: line " + e.getLineNumber() + ": " + e.getMessage());
	}

	public void error(SAXParseException e) {
		System.err.println("ERROR: line " + e.getLineNumber() + ": " + e.getMessage());
	}

	public void fatalError(SAXParseException e) throws SAXException {
		System.err.println("FATAL: line " + e.getLineNumber() + ": " + e.getMessage());
		throw (e);
	}
}
