package by.htp.parcer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.SAXException;
import by.htp.parcer.entity.Family;

public class MainFamilyDOM {

	public static void main(String[] args) throws SAXException, IOException {

		List<Family> familyXML = new ArrayList<Family>();
		familyXML = FamilyDOM.parseFamilyXML();

	}

}
