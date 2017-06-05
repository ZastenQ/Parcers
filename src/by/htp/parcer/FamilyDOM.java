package by.htp.parcer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import by.htp.parcer.entity.Child;
import by.htp.parcer.entity.Family;
import by.htp.parcer.entity.Father;
import by.htp.parcer.entity.Mother;
import by.htp.parcer.entity.TagName;

public class FamilyDOM {
	public static List<Family> parseFamilyXML() throws SAXException, IOException {

		List<Family> familyXML = new ArrayList<Family>();
		Family familyEntity = null;
		Mother familyMother = null;
		Father familyFather = null;
		Child familyChild = null;
		List<Child> familyChildren = null;

		DOMParser parcer = new DOMParser();
		parcer.parse("resources/FamilyTree.xml");
		Document document = parcer.getDocument();

		Element root = document.getDocumentElement();

		NodeList rootList = root.getChildNodes();
		for (int i = 1; i < rootList.getLength(); i += 2) {
			Node rootNode = rootList.item(i);
			TagName tag = TagName.getElementTagName(rootNode.getNodeName());

			if (TagName.FAMILY == tag) {
				familyEntity = new Family();
				familyXML = new ArrayList<Family>();
				String attributeID = rootNode.getAttributes().getNamedItem("id").getNodeValue();
				familyEntity.setFamilyID(Integer.parseInt(attributeID));

				NodeList childList = rootNode.getChildNodes();
				for (int j = 1; j < childList.getLength(); j += 2) {
					Node childNode = childList.item(j);
					TagName childTag = TagName.getElementTagName(childNode.getNodeName());

					if (TagName.MOTHER == childTag) {
						familyMother = new Mother();

						NodeList elementList = childNode.getChildNodes();
						for (int k = 1; k < elementList.getLength(); k += 2) {

							Node elementNode = elementList.item(k);
							TagName elementTag = TagName.getElementTagName(elementNode.getNodeName());
							String elementText = elementNode.getTextContent();

							if (TagName.NAME == elementTag) {
								familyMother.setName(elementText);
							} else if (TagName.SURNAME == elementTag) {
								familyMother.setSurname(elementText);
							} else if (TagName.AGE == elementTag) {
								familyMother.setAge(Integer.parseInt(elementText));
							} else if (TagName.MAIDEN_NAME == elementTag) {
								familyMother.setMaidenName(elementText);
							}
						}

						familyEntity.setFamilyMother(familyMother);

					} else if (TagName.FATHER == childTag) {
						familyFather = new Father();

						NodeList elementList = childNode.getChildNodes();
						for (int k = 1; k < elementList.getLength(); k += 2) {

							Node elementNode = elementList.item(k);
							TagName elementTag = TagName.getElementTagName(elementNode.getNodeName());
							String elementText = elementNode.getTextContent();

							if (TagName.NAME == elementTag) {
								familyFather.setName(elementText);
							} else if (TagName.SURNAME == elementTag) {
								familyFather.setSurname(elementText);
							} else if (TagName.AGE == elementTag) {
								familyFather.setAge(Integer.parseInt(elementText));
							}
						}

						familyEntity.setFamilyFather(familyFather);

					} else if (TagName.CHILDREN == childTag) {
						familyChildren = new ArrayList<Child>();

						NodeList elementList = childNode.getChildNodes();
						for (int k = 1; k < elementList.getLength(); k += 2) {

							Node elementNode = elementList.item(k);
							TagName elementTag = TagName.getElementTagName(elementNode.getNodeName());

							if (TagName.CHILD == elementTag) {
								familyChild = new Child();

								String attrGender = elementNode.getAttributes().getNamedItem("gender").getNodeValue();
								String attrDateBirth = elementNode.getAttributes().getNamedItem("date-of-birth")
										.getNodeValue();
								familyChild.setGender(attrGender);
								familyChild.setDateBirth(attrDateBirth);

								NodeList subList = elementNode.getChildNodes();
								for (int x = 1; x < subList.getLength(); x += 2) {

									Node subNode = subList.item(x);
									TagName subTag = TagName.getElementTagName(subNode.getNodeName());
									String subText = subNode.getTextContent();

									if (TagName.NAME == subTag) {
										familyChild.setName(subText);
									} else if (TagName.SURNAME == subTag) {
										familyChild.setSurname(subText);
									}
								}
								familyChildren.add(familyChild);
							}
						}
					}
					familyEntity.setFamilyChildren(familyChildren);
				}
			}
			familyXML.add(familyEntity);
		}

		return familyXML;
	}

}
