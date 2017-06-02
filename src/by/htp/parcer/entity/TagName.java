package by.htp.parcer.entity;

public enum TagName {
	FAMILIES, FAMILY, MOTHER, FATHER, CHILDREN, CHILD, NAME, SURNAME, AGE, MAIDEN_NAME;

	public static TagName getElementTagName(String element) {
		switch (element) {
		case "families":
			return FAMILIES;
		case "family":
			return FAMILY;
		case "mother":
			return MOTHER;
		case "father":
			return FATHER;
		case "children":
			return CHILDREN;
		case "child":
			return CHILD;
		case "name":
			return NAME;
		case "surname":
			return SURNAME;
		case "age":
			return AGE;
		case "maiden-name":
			return MAIDEN_NAME;
		default:
			throw new EnumConstantNotPresentException(TagName.class, element);
		}
	}
}
