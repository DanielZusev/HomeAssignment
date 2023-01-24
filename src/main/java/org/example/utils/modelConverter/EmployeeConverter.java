package org.example.utils.modelConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.Employee;
import org.example.utils.logger.MyLogger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class EmployeeConverter {
    private static JSONParser jsonParser;
    private static ObjectMapper objectMapper;

    private static void initConverter() {
        jsonParser = new JSONParser();
        objectMapper = new ObjectMapper();
    }

    /**
     * Json convertor for single Employee response
     */
    public static Employee toEmployee(String json) {
        try {
            initConverter();
            JSONObject employeeJson = (JSONObject) jsonParser.parse(json);
            String employeeData = employeeJson.get("data").toString();

            return objectMapper.readValue(employeeData, Employee.class);

        } catch (ParseException | JsonProcessingException e) {
            MyLogger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Employee converter to Json object for post calls
     */
    public static String toJsonEmployee(Employee employee) {
        JSONObject jsonEmployee = new JSONObject();

        if (employee.getEmployee_name() != null)
            jsonEmployee.put("employee_name", employee.getEmployee_name());

        if (employee.getProfile_image() != null)
            jsonEmployee.put("profile_image", employee.getProfile_image());

        jsonEmployee.put("employee_age", employee.getEmployee_age());
        jsonEmployee.put("employee_salary", employee.getEmployee_salary());

        return jsonEmployee.toJSONString();
    }

    /**
     * Json converter for multiple Employees response
     */
    public static List<Employee> toEmployees(String json) {
        List<Employee> employeesList;
        try {
            initConverter();
            employeesList = new ArrayList<>();

            JSONObject employeesJson = (JSONObject) jsonParser.parse(json);
            JSONArray employeesArrayJson = (JSONArray) employeesJson.get("data");

            for (Object obj : employeesArrayJson) {
                String employeeData = obj.toString();
                employeesList.add(objectMapper.readValue(employeeData, Employee.class));
            }

        } catch (ParseException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return employeesList;
    }
}
