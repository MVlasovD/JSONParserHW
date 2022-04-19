/*
 *
 * @author VMN
 *
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static List<Employee> employees = new ArrayList<>();
    private static String json;
    private static String str;
    private static StringBuilder sb = new StringBuilder();

    private static String readString(String fileNameJson) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(fileNameJson));
            JSONArray jsonArray = (JSONArray) obj;
            String[] arr = jsonArray.toString()
                    .replace("},{", "}!{")
                    .replace("[", "")
                    .replace("]", "")
                    .split("!");
            json = Arrays.toString(arr);
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return json;
    }

    private static List<Employee> jsonToList(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(json);
        for (int i = 0; i > jsonArray.size(); i++) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Employee employee = gson.fromJson((String) jsonArray][i], Employee.class);
            employees.add(employee);
        }


//        При реализации метода jsonToList() вам потребуются такие объекта как: JSONParser,
//        GsonBuilder, Gson.
//        JSONParser даст вам возможность с помощью метода parse() получить
//        из строчки json массив JSONArray.
//        GsonBuilder будет использован исключительно для создания
//        экземпляра Gson.
//        Пройдитесь циклом по всем элементам jsonArray и преобразуйте все jsonObject
//        в Employee.class с помощью метода gson.fromJson().
//        Полученные экземпляры класса Employee добавляйте
//        в список, который должен быть выведен из метода после его окончания.

        return employees;
    }


    public static void main(String[] args) throws IOException, ParseException {

        String json = readString("new_data.json");
        List<Employee> list = jsonToList(json);
        System.out.println(list);
    }
}