import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class CallingRequests {

    @Test
    public void getMethod(){
        APIRequests apiRequests = new APIRequests();
        Response response = apiRequests.findLocation2("-33.8670522","151.1957362", "1500");
        Assert.assertTrue(apiRequests.isSearchedNameFound("Sydney", response));
    }
}
