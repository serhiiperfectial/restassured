import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class CallingRequests {

    @Test
    public void tryMethod(){
        APIRequests apiRequests = new APIRequests();
        Response response = apiRequests.findLocation("-33.8670522","151.1957362", "1500");
        Assert.assertTrue(apiRequests.isSearchedNameFound("Sydney", response));
    }
}
