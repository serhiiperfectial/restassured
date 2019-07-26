import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;


public class CallingRequests {

    APIRequests apiRequests = new APIRequests();

    @Test
    public void getMethod(){
        Response response = apiRequests.findLocation2("-33.8670522","151.1957362", "1500");
        Assert.assertTrue(apiRequests.isSearchedNameFound("Sydney", response));
    }

    @Test
    public void createChannel() {
        Response res = apiRequests.createNewChannelFormRequest("hello_third1_channel");
        Assert.assertTrue(apiRequests.isOK(res), "not created. Error: " + apiRequests.getErrorFromResponse(res));
    }

    @Test
    public void setChannelTopic() {
        String topic = "New topic";
        String channelName = "hello_third1_channel";
        String channelID = "";
        for (Map<String, String> channel : apiRequests.getChannelsList()){
            if (channel.get("name").equals(channelName))
                channelID = channel.get("id");
        }
        Response res = apiRequests.setChannelTopic(channelID, topic);
        Assert.assertTrue(apiRequests.isTopicCreated(res, topic), "Error: " + apiRequests.getErrorFromResponse(res));
    }

}
