import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;
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
        String channelID = apiRequests.getChannelIDByChannelName(channelName);

        Response res = apiRequests.setChannelTopic(channelID, topic);
        Assert.assertTrue(apiRequests.isTopicCreated(res, topic), "Error: " + apiRequests.getErrorFromResponse(res));
    }

    @Test
    public void renameChannel() {
        String oldChannelName = "hello_third1_channel";
        String newName = "Edited-name";
        String channelID = apiRequests.getChannelIDByChannelName(oldChannelName);
        Response response = apiRequests.renameChannel(channelID, newName);
        Assert.assertEquals(apiRequests.getChannelNameByID(channelID), newName.toLowerCase(), "Error: " + apiRequests.getErrorFromResponse(response));
    }

    @Test
    public void addUserToWorkspace() {
        String userEmail = "clickatelltest123@gmail.com";
        String channelName = "edited-name";
        Response response = apiRequests.inviteUserToWorkspaceByEmail(apiRequests.getChannelIDByChannelName(channelName), userEmail);
        List<String> addedUsers = apiRequests.getUserNames();
        for (String line : addedUsers)
            System.out.println(line);
        Assert.assertTrue(apiRequests.isOK(response), "Error: " + apiRequests.getErrorFromResponse(response));
    }

    @Test
    public void kickUserFromChannel() {
        String userID = apiRequests.getUserIDByName("clickatelltest123");
        String channelID = apiRequests.getChannelIDByChannelName("edited-name");
        apiRequests.getChannelMembers(channelID);
        Response response = apiRequests.kickUserFromChannel(channelID, userID);
        Assert.assertFalse(apiRequests.getChannelMembers(channelID).contains(userID), "Error: " + apiRequests.getErrorFromResponse(response));
    }

    @Test
    public void addUserToChannel() {
        String userID = apiRequests.getUserIDByName("clickatelltest123");
        String channelID = apiRequests.getChannelIDByChannelName("edited-name");
        Response response = apiRequests.inviteUserToChannel(channelID, userID);
        Assert.assertTrue(apiRequests.getChannelMembers(channelID).contains(userID), "Error: " + apiRequests.getErrorFromResponse(response));
    }
}
