package study;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Date;

import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
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
				Element rootElement = document.getRootElement();
				System.out.println(rootElement.getName());
				List<Element> lstChildren = rootElement.getChildren();
				for (Element item : lstChildren) {
					for (Attribute attr : item.getAttributes()) {
						if (attr.getName().equalsIgnoreCase("id")) {
							attr.setValue(attr.getValue() + "_1");
						}
						
						System.out.println(String.format("%s:%s", attr.getName(), attr.getValue()));
					}
					
					for (Element itmChild : item.getChildren()) {
						System.out.println(String.format("%s:%s -> Text:%s", itmChild.getName(),
								itmChild.getValue(), itmChild.getTextTrim()));
					}
					
					if (item.getChildren().size() > 0) {
						Date currentDate = new Date();
						if (item.getChild("age") == null) {
							Element age = new Element("age").setText("28");
							age.setAttribute("addTime", String.format("%tc%n", currentDate));
							item.addContent(age);
						}
						
						Element eleTime = item.getChild("UpdateTime");
						
						if (eleTime == null) {
							eleTime = new Element("UpdateTime").setText(String.format("%tc", currentDate));
							item.addContent(eleTime);
						} else {
							eleTime.setText(String.format("%tc", currentDate));
						}
					}
				}
				
				rootElement.addContent(new Comment("Test for add Comment"));
				XMLOutputter xmlOutput = new XMLOutputter();
				// display nice nice
				xmlOutput.setFormat(Format.getPrettyFormat());
				xmlOutput.output(document, new FileWriter(xmlFilePath));
				
				// List listElement = rootElement.getChildren("employee");
				// for (int i = 0; i < listElement.size(); i++) {
				// Element node = (Element) listElement.get(i);
				// System.out.println("ID : " + node.getAttribute("id"));
				// System.out.println("First Name : "
				// + node.getChildText("firstname"));
				// System.out.println("Last Name : "
				// + node.getChildText("lastname"));
				// System.out.println("Email : " + node.getChildText("email"));
				// System.out.println("Department : "
				// + node.getChildText("department"));
				// System.out.println("Salary : " +
				// node.getChildText("salary"));
				// System.out.println("Address : " +
				// node.getChildText("address"));
				// }
				
			} catch (IOException io) {
				System.out.println(io.getMessage());
			} catch (JDOMException jdomex) {
				System.out.println(jdomex.getMessage());
			}
		}
	}
}
