package study;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;

import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Parent;
import org.jdom2.filter.ElementFilter;
import org.jdom2.filter.Filter;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import util.windowsOS.WinUtil;

public class ReadXMLFileWithJDOM {
	
	public static void main(String[] args) {
		String strPath = new StringBuffer(System.getProperty("user.dir")).append(File.separator)
				.append("src").toString();
		
		System.out.println(strPath);
		List<String> lstFile = (new WinUtil()).listFilesForFolderByExt(new File(strPath), ".xml");
		for (String xmlFilePath : lstFile) {
			System.out.println(xmlFilePath);
			
			SAXBuilder saxBuilder = new SAXBuilder();
			// String xmlFilePath = "./src/testFile.xml";
			File xmlFile = new File(xmlFilePath);
			
			try {
				Document document = saxBuilder.build(xmlFile);
				DocType docType = document.getDocType();
				if (docType != null)
					System.out.println("DOCTYPE: " + docType.getSystemID());
				
				Element rootElement = document.getRootElement();
				System.out.println(rootElement.getName());
				ListChildren(rootElement);
				
				Iterator<?> iterator = document.getDescendants(new ElementFilter("employee"));
				while (iterator.hasNext()) {
					Element elem = (Element) iterator.next();
					
					for (Attribute attr : elem.getAttributes()) {
						if (attr.getName().equalsIgnoreCase("id") && !attr.getValue().endsWith("_1")) {
							attr.setValue(attr.getValue() + "_1");
						}
						
						System.out.println(String.format("%s:%s", attr.getName(), attr.getValue()));
					}
					
					Date currentDate = new Date();
					String logValue = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(currentDate);
					System.out.println(logValue);
					if (elem.getChild("age") == null) {
						Element age = new Element("age").setText("28");
						age.setAttribute("addTime", logValue);
						elem.addContent(age);
					} else {
						elem.getChild("age").setAttribute("addTime", logValue);
					}
					
					Element eleTime = elem.getChild("updateTime");
					
					if (eleTime == null) {
						eleTime = new Element("updateTime").setText(String.format("%tc", currentDate));
						elem.addContent(eleTime);
					} else {
						eleTime.setText(logValue);
					}
				}
				
				String newCommentText = "Test for add Comment";
				if (!CheckElementCommentExist(rootElement, newCommentText)) {
					rootElement.addContent(new Comment(newCommentText));
				}
				
				XMLOutputter xmlOutput = new XMLOutputter();
				// display nice
				xmlOutput.setFormat(Format.getPrettyFormat());
				xmlOutput.output(document, new FileWriter(xmlFilePath));
				
				if (rootElement.getName().equalsIgnoreCase("company")) {
					List<Employee> lstEmployee = getEmployees(rootElement);
					for (Employee itm : lstEmployee) {
						System.out.println(itm.toString());
					}
					
					Filter<Element> filter = new ElementFilter("employee");
					Iterator<Element> dit = document.getDescendants(filter);
					
					while (dit.hasNext()) {
						Element element = (Element) dit.next();
						Employee e = getEmployee(element);
						System.out.println(e.toString());
					}
				}
			} catch (IOException io) {
				System.out.println(io.getMessage());
			} catch (JDOMException jdomex) {
				System.out.println(jdomex.getMessage());
			}
		}
	}
	
	public static boolean CheckElementCommentExist(Element element, String commentText) {
		boolean exist = false;
		Iterator<Comment> iteratorComment = element.getContent(Filters.comment()).iterator();
		while (iteratorComment.hasNext()) {
			Comment c = iteratorComment.next();
			System.out.println(c.getText());
			if (c.getText().equals(commentText))
				exist = true;
		} // end while
		
		// Without Filter to check
		// List<Content> content = element.getContent();
		// Iterator<Content> iterator = content.iterator();
		// while (iterator.hasNext()) {
		// Object o = iterator.next();
		// if (o instanceof Comment) {
		// Comment c = (Comment) o;
		// if (c.getText().equals(commentText))
		// exist = true;
		// }
		// } // end while
		
		return exist;
	}
	
	private static String GetElementDesc(Element eleItem) {
		StringBuffer rsltSB = new StringBuffer();
		rsltSB.append(eleItem.getName());
		Parent parentItem = eleItem.getParent();
		while (parentItem != null) {
			if (parentItem instanceof Element)
				rsltSB.insert(0, "->").insert(0, ((Element) parentItem).getName());
			parentItem = parentItem.getParent();
		}
		
		return rsltSB.toString();
	}
	
	private static void ListChildren(Element eleItem) {
		for (Attribute attr : eleItem.getAttributes()) {
			System.out.println(String.format("%s->%s:%s", GetElementDesc(eleItem), attr.getName(),
					attr.getValue()));
		}
		
		if (eleItem.getContentSize() == 1) {
			System.out.println(String.format("%s:%s -> Text:%s", GetElementDesc(eleItem), eleItem.getValue(),
					eleItem.getTextTrim()));
		}
		
		for (Element itmChild : eleItem.getChildren()) {
			List<Element> lstChildren = itmChild.getChildren();
			if (lstChildren.size() == 0) {
				for (Attribute attr : itmChild.getAttributes()) {
					System.out.println(String.format("%s->%s:%s", GetElementDesc(itmChild), attr.getName(),
							attr.getValue()));
				}
				
				System.out.println(String.format("%s:%s -> Text:%s", GetElementDesc(itmChild),
						itmChild.getValue(), itmChild.getTextTrim()));
			} else {
				for (Element itmGrandson : lstChildren) {
					ListChildren(itmGrandson);
				}
			}
		}
	}
	
	private static List<Employee> getEmployees(Element root) {
		List<Employee> lstEmployee = new ArrayList<Employee>();
		List<Element> empChildren = root.getChildren("employee");
		for (Iterator<Element> it = empChildren.iterator(); it.hasNext();) {
			Element entryChild = (Element) it.next();
			Employee e = getEmployee(entryChild);
			lstEmployee.add(e);
		}
		
		return lstEmployee;
	}
	
	private static Employee getEmployee(Element e) {
		Element element = e.getChild("name");
		EmployeeName name = getEmployeeName(element);
		String id = e.getAttributeValue("id", "");
		String email = e.getChildText("email");
		String department = e.getChildText("department");
		String salary = e.getChildText("salary");
		String address = e.getChildText("address");
		String age = e.getChildText("age");
		String updateTime = e.getChildText("updateTime");
		Employee rslt = new Employee(id, name, email, department, salary, address, age);
		rslt.setUpdateTime(updateTime);
		return rslt;
	}
	
	private static EmployeeName getEmployeeName(Element e) {
		String first = e.getChildText("firstname");
		String last = e.getChildText("lastname");
		return new EmployeeName(first, last);
	}
}

class EmployeeName {
	private String firstName;
	private String lastName;
	
	public EmployeeName() {
	}
	
	public EmployeeName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return "EmployeeName [firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}

class Employee {
	private String id;
	private EmployeeName empName;
	private String email;
	private String department;
	private String salary;
	private String address;
	private String age;
	private String updateTime;
	
	public Employee() {
	}
	
	public Employee(String id, EmployeeName empName, String email, String department, String salary,
			String address, String age) {
		this.id = id;
		this.empName = empName;
		this.email = email;
		this.department = department;
		this.salary = salary;
		this.address = address;
		this.age = age;
	}
	
	public EmployeeName getEmpName() {
		return empName;
	}
	
	public void setEmpName(EmployeeName empName) {
		this.empName = empName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
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
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", empName=" + empName.toString() + ", email=" + email
				+ ", department=" + department + ", salary=" + salary + ", address=" + address + ", age="
				+ age + ", updateTime=" + updateTime + "]";
	}
	
}
