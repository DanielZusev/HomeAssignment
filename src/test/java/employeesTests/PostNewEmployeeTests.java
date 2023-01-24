package employeesTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.models.Employee;
import org.example.utils.logger.MyLogger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.example.consts.Route.employeeUrl;
import static org.example.consts.Route.postNewEmployee;
import static org.example.utils.modelConverter.EmployeeConverter.toEmployee;
import static org.example.utils.modelConverter.EmployeeConverter.toJsonEmployee;
import static org.example.utils.report.ExtentTestManager.startTest;

public class PostNewEmployeeTests {

    Employee newEmployee;
    private Response res;

    @BeforeTest
    public void init() {
        startTest(this.getClass().getName(), "Tests related to Post new employee from API");
        newEmployee = new Employee("tester test", 36000, 45);
    }

    @Test
    public void checkPostCallResponseStatus() {
        MyLogger.info("Check API Add new Employee Call Response Status");

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(toJsonEmployee(newEmployee));

        this.res = request.post(employeeUrl + postNewEmployee);

        if (res.statusCode() == 200)
            MyLogger.info("API Post new employee Call Response Status 200");
        else {
            MyLogger.error("API Post New Employee Call Failed");
            Assert.assertEquals(res.statusCode(), 200);
        }
    }

    @Test(priority = 1)
    public void checkPostCallReturnTheSameValues() {
        MyLogger.info("Check API Add new Employee Call return same employee values");
        boolean isSameEmployee = toEmployee(res.getBody().asString()).equals(newEmployee);

        if (isSameEmployee)
            MyLogger.info("Same employee value has returned");
        else
            MyLogger.error("Different employee values returned from API");

    }
}
