import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

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
        String oldChannelName = "hello_third_channel";
        String newName = "Edited-name2";
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

    @Test
    public void deleteChannel() {
        String channelID = apiRequests.getChannelIDByChannelName("qwertyuio");
        Response response = apiRequests.deleteChannel(channelID);
        Assert.assertTrue(apiRequests.isOK(response), "Error: " + apiRequests.getErrorFromResponse(response));
    }

    @Test
    public void sendMessageToChannel() {
        String channelID = apiRequests.getChannelIDByChannelName("edited-name");
        String textMessage = "Whazaaaaaaaappppp";
        Response response = apiRequests.postMessage(channelID, textMessage, false);
        Assert.assertEquals(response.getBody().jsonPath().get("message.text"), textMessage, "Error: " + apiRequests.getErrorFromResponse(response));
    }

    @Test
    public void deleteMessage(){
        String message = "Whazaaaaaaaappppp";
        String userID = apiRequests.getUserIDByName("Serhii");
        String channelID = apiRequests.getChannelIDByChannelName("edited-name");
        String messageTimestamp = apiRequests.getMessageTimestampInChannel(channelID, userID, true, message);
        Response response = apiRequests.deleteMessageFromChannel(channelID, messageTimestamp, true);
        Assert.assertTrue(apiRequests.isOK(response) && !apiRequests.isMessageInHistory(channelID, messageTimestamp), "Error: " + apiRequests.getErrorFromResponse(response));
    }

    @Test
    public void updateMessage() {
        String oldMessage = "Old message";
        String updatedMessage = "Updated message";
        String channelID = apiRequests.getChannelIDByChannelName("edited-name");
        String userID = apiRequests.getUserIDByName("Serhii");
        apiRequests.postMessage(channelID, oldMessage, true);
        String messageToUpdateTimestamp = apiRequests.getMessageTimestampInChannel(channelID, userID, true, oldMessage);
        Response response = apiRequests.updateMessage(channelID, messageToUpdateTimestamp, updatedMessage, true);
        Assert.assertTrue(apiRequests.isOK(response) && (apiRequests.getMessageTextByTimestamp(channelID, messageToUpdateTimestamp).equals(updatedMessage)),
                "Error: " + apiRequests.getErrorFromResponse(response));
    }

    // used specific token type
//    @Test
//    public void getPermissionsList() {
//        String appName = "perf_slack";
//        String[] list = apiRequests.getPermissionsFor(appName);
//        for (String aList : list) System.out.println(aList);
//    }
}
