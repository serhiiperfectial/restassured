import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class CallingRequests {

    APIRequests apiRequests = new APIRequests();

    @Test
    public void getMethod(){
        Response response = apiRequests.findLocation2("-33.8670522","151.1957362", "1500");
        Assert.assertTrue(apiRequests.isSearchedNameFound("Sydney", response));
    }

    @Test
    public void createChannel() {
        SoftAssert softAssert = new SoftAssert();
        Response res = apiRequests.createNewChannel("hello_third_channel");
        softAssert.assertEquals(200, res.statusCode(), "not equal");
        softAssert.assertTrue(res.getBody().jsonPath().get("ok").equals(true), "not created");
        softAssert.assertAll();
    }
}
