package employeesTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.models.Employee;
import org.example.utils.logger.MyLogger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.example.consts.Route.*;
import static org.example.utils.modelConverter.EmployeeConverter.toEmployee;
import static org.example.utils.modelConverter.EmployeeConverter.toJsonEmployee;
import static org.example.utils.report.ExtentTestManager.startTest;

public class GetSpecificEmployeeTests {

    Employee newEmployee;
    private Response res;

    @BeforeTest
    public void init() {
        startTest(this.getClass().getName(), "Tests related to get specific employee from API");
        newEmployee = new Employee("tester test", 36000, 45);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(toJsonEmployee(newEmployee));

        this.res = request.post(employeeUrl + postNewEmployee);

        Assert.assertEquals(res.statusCode(), 200);
    }

    @Test
    public void checkGetCreatedEmployeeByIdResponseStatus() {
        MyLogger.info("Check API Get Specific Employee Call Response Status");

        Employee createdEmployee = toEmployee(res.getBody().asString());

        this.res = RestAssured.get(employeeUrl + getSpecificEmployee + createdEmployee.getId());

        if (res.statusCode() == 200)
            MyLogger.info("API Get Specific Employee Call Response Status 200");
        else {
            MyLogger.error("API Get Specific Employees Call Failed");
            Assert.assertEquals(res.statusCode(), 200);
        }
    }

    @Test
    public void checkGetCreatedEmployeeByIdReturnSameEmployee() {
        MyLogger.info("Check API Get Specific Employee Call return same values that sent by post call");

        Employee postCreatedEmployee = toEmployee(res.getBody().asString());
        this.res = RestAssured.get(employeeUrl + getSpecificEmployee + postCreatedEmployee.getId());

        Employee getCreatedEmployee = toEmployee(res.getBody().asString());

        if (postCreatedEmployee.equals(getCreatedEmployee))
            MyLogger.info("Correct values returned from API");
        else
            MyLogger.info("Wrong Employee returned from API");
    }


}
