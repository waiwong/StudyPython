package study;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ParseQAList {
	
	public static void main(String[] args) {
		// String xmlFilePath = new StringBuffer(
		// (new WinUtil()).getCurrentUserDesktopPath())
		// .append(File.separator).append("QA_FULL_LIST.txt")
		// .toString();
		
		String xmlFilePath = new StringBuffer(System.getProperty("user.dir")).append(File.separator)
				.append("QA_FULL_LIST.xml").toString();
		
		System.out.println(xmlFilePath);
		
		SAXBuilder saxBuilder = new SAXBuilder();
		File xmlFile = new File(xmlFilePath);
		
		try {
			Document jdomDoc = saxBuilder.build(xmlFile);
			Element rootElement = jdomDoc.getRootElement();
			System.out.println(rootElement.getName());
			ListChildren(rootElement);
			
			// List<Element> lstChildren = rootElement.getChildren();
			// for (Element item : lstChildren) {
			// for (Attribute attr : item.getAttributes()) {
			// System.out.println(String.format("%s:%s", attr.getName(),
			// attr.getValue()));
			// }
			//
			// for (Element itmChild : item.getChildren()) {
			// ListChildren(itmChild);
			// // break;
			// }
			// }
			XMLOutputter xmlOutput = new XMLOutputter();
			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(jdomDoc, new FileWriter(xmlFilePath));
			System.out.println("Done");
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
	}
	
	private static void ListChildren(Element eleItem) {
		for (Attribute attr : eleItem.getAttributes()) {
			System.out.println(String.format("%s:%s", attr.getName(), attr.getValue()));
		}
		
		if (eleItem.getContentSize() == 1) {
			System.out.println(String.format("%s:%s -> Text:%s", eleItem.getName(), eleItem.getValue(),
					eleItem.getTextTrim()));
		}
		
		for (Element itmChild : eleItem.getChildren()) {
			List<Element> lstChildren = itmChild.getChildren();
			if (lstChildren.size() == 0) {
				System.out.println(String.format("%s:%s -> Text:%s", itmChild.getName(), itmChild.getValue(),
						itmChild.getTextTrim()));
			} else {
				for (Element itmGrandson : lstChildren) {
					ListChildren(itmGrandson);
				}
			}
		}
	}
}
