package api.test;

import api.endpoints.UserEndPoints;
import api.payloads.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DDTests {
    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testPostUser(String UserID, String UserName, String FirstName, String LastName, String Email, String Password, String Mob) {
        User userPayload = new User();

        userPayload.setId(Integer.parseInt(UserID));
        userPayload.setUsername(UserName);
        userPayload.setFirstName(FirstName);
        userPayload.setLastName(LastName);
        userPayload.setEmail(Email);
        userPayload.setPassword(Password);
        userPayload.setPhone(Mob);

        Response response= UserEndPoints.createUser(userPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);

    }

    @Test(priority = 2,dataProvider = "UserNames",dataProviderClass = DataProviders.class)
    public void testdeleteUser(String UserName){
        Response response=UserEndPoints.deleteUser(UserName);
        Assert.assertEquals(response.getStatusCode(),200);
    }
}
