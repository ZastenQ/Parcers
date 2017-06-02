package by.htp.parcer.entity;

import java.util.List;

public class Family {
	private Father familyFather;
	private Mother familyMother;
	private List<Child> familyChildren;
	private int familyID;

	public Father getFamilyFather() {
		return familyFather;
	}

	public void setFamilyFather(Father familyFather) {
		this.familyFather = familyFather;
	}

	public Mother getFamilyMother() {
		return familyMother;
	}

	public void setFamilyMother(Mother familyMother) {
		this.familyMother = familyMother;
	}

	public List<Child> getFamilyChildren() {
		return familyChildren;
	}

	public void setFamilyChildren(List<Child> familyChildren) {
		this.familyChildren = familyChildren;
	}

	public int getFamilyID() {
		return familyID;
	}

	public void setFamilyID(int familyID) {
		this.familyID = familyID;
	}

}
