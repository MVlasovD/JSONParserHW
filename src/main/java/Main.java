/*
 *
 * @author VMN
 *
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static String listToJson(final List<Employee> list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        String json = gson.toJson(list, listType);
        return json;
    }

    public static void writeString(String json, String fileNameJson) throws IOException {
        FileWriter file = new FileWriter(fileNameJson);
        file.write(json);
        file.flush();
        System.out.println(
                "File " + "'" + fileNameXMLst + "'" + " converted into " + "'" + fileNameJson + "'");
    }

    private static List<Employee> employees = new ArrayList<>();

    private static List<Employee> parseXML(String fileNameXML)
            throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileNameXML));
        fileNameXMLst = fileNameXML;
        Node root = doc.getDocumentElement();
        read(root);
        return employees;
    }

    private static void read(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node ele = nodeList.item(i);
            if (ele.getNodeType() != Node.TEXT_NODE) {
                Element employee = (Element) ele;
                employees.add(
                        new Employee(Long.parseLong(employee.getElementsByTagName("id").item(0).getTextContent()),
                                employee.getElementsByTagName("firstName").item(0).getTextContent(),
                                employee.getElementsByTagName("lastName").item(0).getTextContent(),
                                employee.getElementsByTagName("country").item(0).getTextContent(),
                                Integer.parseInt(employee.getElementsByTagName("age").item(0).getTextContent())));
            }
        }

    }

    private static String fileNameXMLst;

    public static void main(String[] args) throws
            IOException, ParserConfigurationException, SAXException {

        List<Employee> listXML = parseXML("data.xml");
        String fileNameJson = "data2.json";
        String json = listToJson(listXML);
        writeString(json, fileNameJson);
    }
}