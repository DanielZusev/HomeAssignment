package employeesTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.models.Employee;
import org.example.utils.logger.MyLogger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.example.consts.Route.employeeUrl;
import static org.example.consts.Route.getAllEmployees;
import static org.example.utils.modelConverter.EmployeeConverter.toEmployees;
import static org.example.utils.report.ExtentTestManager.startTest;

public class GetAllEmployeesTests {
    private Response res;

    @BeforeTest
    public void init() {
        startTest(this.getClass().getName(), "Tests related to Get all employees from API");
    }

    @Test
    public void checkGetCallResponseStatus() {
        MyLogger.info("Check API Get All Employees Call Response Status");

        this.res = RestAssured.get(employeeUrl + getAllEmployees);

        if (res.statusCode() == 200)
            MyLogger.info("API Get All Employees Call Response Status 200");
        else {
            MyLogger.error("API Get All Employees Call Failed");
            Assert.assertEquals(res.statusCode(), 200);
        }
    }

    @Test(priority = 1)
    public void checkResponseHasValues() {
        MyLogger.info("Check if there are any employees in the company");

        List<Employee> employeeList = toEmployees(res.getBody().asString());

        if (employeeList.size() > 0)
            MyLogger.info("There are " + employeeList.size() + " employees in the company");
        else {
            MyLogger.error("No employees in the company");
            Assert.assertTrue(employeeList.size() > 0, "No employees in the company");
        }

    }

    @Test(priority = 2)
    public void checkEmployeeListForDuplicates() {
        MyLogger.info("Check if there are duplicate employees in the company");

        List<Employee> employeeList = toEmployees(res.getBody().asString());
        boolean hasDuplicates = false;

        for (int i = 0; i < employeeList.size() - 1; i++) {
            for (int j = i + 1; j < employeeList.size() - 1; j++) {
                if (employeeList.get(i).equals(employeeList.get(j))) {
                    hasDuplicates = true;
                    break;
                }
            }
        }
        Assert.assertFalse(hasDuplicates, "There are duplicate employees in the company");
    }

}
