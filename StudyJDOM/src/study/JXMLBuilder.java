package study;

import java.io.*;
import java.util.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class JXMLBuilder {
	Document build(List<Entry> entries) {
		Element root = new Element("phoneNumbers");
		Element title = new Element("title");
		title.addContent("Phone Numbers");
		root.addContent(title);
		
		Element entriesEle = createEntries(entries);
		root.addContent(entriesEle);
		return new Document(root);
	}
	
	private Element createEntries(List<Entry> entries) {
		Element e = new Element("entries");
		for (Entry anEntry : entries)
			e.addContent(createEntry(anEntry));
		return e;
	}
	
	private Element createEntry(Entry anEntry) {
		Element e = new Element("entry");
		e.addContent(createName(anEntry.getEntryName(), anEntry.getGender()));
		e.addContent(new Element("phone").setText(anEntry.getPhone()));
		String city = anEntry.getCity();
		if (city != null && !city.equals(""))
			e.addContent(new Element("city").setText(city));
		return e;
	}
	
	private Element createName(Name n, String gender) {
		Element e = new Element("name");
		if (gender != null && !gender.equals(""))
			e.setAttribute("gender", gender);
		e.addContent(new Element("first").setText(n.getFirst()));
		
		String middle = n.getMiddle();
		if (middle != null && !middle.equals(""))
			e.addContent(new Element("middle").setText(middle));
		e.addContent(new Element("last").setText(n.getLast()));
		return e;
	}
	
	public static void main(String[] args) throws IOException {
		List<Entry> entries = new ArrayList<Entry>();
		entries.add(new Entry(new Name("Robin", "Banks"), "354-4455"));
		entries.add(new Entry(new Name("Forrest", "Murmers"), "male", "341-6152", "Solon"));
		entries.add(new Entry(new Name("Barb", "A", "Wire"), "337-8182", "Hills"));
		entries.add(new Entry(new Name("Isabel", "Ringing"), "female", "335-5985", null));
		JXMLBuilder xmlBuilder = new JXMLBuilder();
		Document doc = xmlBuilder.build(entries);
		FileWriter result = new FileWriter("newPhone.xml");
		XMLOutputter out = new XMLOutputter();
		Format f = Format.getPrettyFormat();
		out.setFormat(f);
		out.output(doc, result);
		out.output(doc, System.out);
	}
}

class Name {
	
	private String first;
	private String middle;
	private String last;
	
	public Name(String first, String last) {
		super();
		this.first = first;
		this.last = last;
	}
	
	public Name(String first, String middle, String last) {
		super();
		this.first = first;
		this.middle = middle;
		this.last = last;
	}
	
	public String getFirst() {
		return first;
	}
	
	public void setFirst(String first) {
		this.first = first;
	}
	
	public String getMiddle() {
		return middle;
	}
	
	public void setMiddle(String middle) {
		this.middle = middle;
	}
	
	public String getLast() {
		return last;
	}
	
	public void setLast(String last) {
		this.last = last;
	}
}

class Entry {
	private Name entryName;
	private String gender;
	private String phone;
	private String city;
	private String salary;
	private String address;
	private String age;
	private String updateTime;
	
	public Entry(Name entryName, String phone) {
		super();
		this.entryName = entryName;
		this.phone = phone;
	}
	
	public Entry(Name entryName, String gender, String phone, String city) {
		super();
		this.entryName = entryName;
		this.gender = gender;
		this.phone = phone;
		this.city = city;
	}
	
	public Entry(Name entryName, String phone, String city) {
		super();
		this.entryName = entryName;
		this.phone = phone;
		this.city = city;
	}
	
	public Name getEntryName() {
		return entryName;
	}
	
	public void setEntryName(Name entryName) {
		this.entryName = entryName;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getSalary() {
		return salary;
	}
	
	public void setSalary(String salary) {
		this.salary = salary;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAge() {
		return age;
	}
	
	public void setAge(String age) {
		this.age = age;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
