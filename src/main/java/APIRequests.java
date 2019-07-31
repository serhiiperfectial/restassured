import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class APIRequests {


    public Response findLocation(String lat, String lng, String radius){
        return RestAssured.given().contentType(ContentType.JSON).
                queryParam("location", lat + "," + lng).
                queryParam("radius", radius).
                queryParam("key", AuthorizationKeys.getGoogleKey()).
                get(Endpoints.NEARBY_PLACE_QUERRY_PARAM);
    }

    public Response findLocation2(String lat, String lng, String radius){
        return RestAssured.given().contentType(ContentType.JSON).
                pathParam("location", lat + "," + lng).
                pathParam("radius", radius).
                pathParam("key", AuthorizationKeys.getGoogleKey()).
                get(Endpoints.NEARBY_PLACE_PATH_PARAM, "location", "radius", "key");
    }

    public boolean isSearchedNameFound(String name, Response response){
        return response.getBody().jsonPath().getList("results.name").contains(name);
    }


    /** SLACK */

    /** APPS */

    public List<Map<String, Object>> getAllPermissionsScopesList() {
        return given().log().all()
                .contentType(ContentType.URLENC)
                .header("Authorization", AuthorizationKeys.getSlackToken())
                .get(Endpoints.SLACK_APPS + ".permissions.scopes.list")
                .getBody().jsonPath().getList("scopes");
    }

    public String[] getPermissionsFor(String app) {
        for (Map<String, Object> currentApp : getAllPermissionsScopesList())
            if (currentApp.containsKey(app))
                return (String[]) currentApp.get(app);

        return null;
    }

    /** BOT */
    public Map<String, Object> getBotInfo(){
        return given().log().all()
                .contentType(ContentType.URLENC)
                .header("Authorization", AuthorizationKeys.getSlackToken())
                .get(Endpoints.SLACK_BOTS + ".info").getBody().jsonPath().getMap("bot");
    }


    /** CHANNELS */

    public Response createNewChannel(String channelName){
        return given().log().all()
                .header("Authorization", AuthorizationKeys.getSlackToken())
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "\"name\": \""+ channelName + "\",\n" +
                        "\"validate\": true\n" +
                        "}")
                .post(Endpoints.SLACK_CHANNEL + ".create");
    }

    public Response createNewChannelFormRequest(String channelName) {
        return given().log().all()
                .contentType(ContentType.URLENC)
                .header("Authorization", AuthorizationKeys.getSlackToken())
                .queryParam("name", channelName)
                .queryParam("validate", true)
                .post(Endpoints.SLACK_CHANNEL + ".create");
    }

    public boolean isOK(Response response) {
        return response.then().assertThat().extract().body().jsonPath().get("ok").equals(true);
    }

    public String getErrorFromResponse(Response response) {
        return response.getBody().jsonPath().get("error");
    }

    public List<Map<String, String>> getChannelsList(){
        return given().log().all()
                .header("Authorization", AuthorizationKeys.getSlackToken())
                .contentType(ContentType.URLENC)
                .get(Endpoints.SLACK_CHANNEL + ".list").getBody().jsonPath().getList("channels");
    }

    public Response setChannelTopic(String channel, String topic) {
        return given().log().all()
                .contentType(ContentType.URLENC)
                .header("Authorization", AuthorizationKeys.getSlackToken())
                .queryParam("channel", channel)
                .queryParam("topic", topic)
                .post(Endpoints.SLACK_CHANNEL + ".setTopic");
    }

    public boolean isTopicCreated(Response response, String topic) {
        return response.then().assertThat().extract().body().jsonPath().get("topic").equals(topic);
    }

    public String getChannelIDByChannelName(String channelName) {
        for (Map<String, String> channel : getChannelsList()){
            if (channel.get("name").equals(channelName))
                return channel.get("id");
        }
        return null;
    }

    public Map<String, ? extends Object> getChannelInfo(String channelID) {
        return given().log().all()
                .contentType(ContentType.URLENC)
                .header("Authorization", AuthorizationKeys.getSlackToken())
                .formParam("channel", channelID)
                .get(Endpoints.SLACK_CHANNEL + ".info")
                .getBody().jsonPath().getMap("channel");
    }

    public String getChannelNameByID(String channelID) {
        return (String) getChannelInfo(channelID).get("name");
    }

    public List<String> getChannelMembers(String channelID) {
        return (ArrayList<String>) getChannelInfo(channelID).get("members");
    }

    public Response renameChannel(String channelID, String newName) {
        return given().log().all()
                .contentType(ContentType.URLENC)
                .header("Authorization", AuthorizationKeys.getSlackToken())
                .formParam("channel", channelID)
                .formParam("name", newName)
                .post(Endpoints.SLACK_CHANNEL + ".rename");
    }

    public Response kickUserFromChannel(String channelID, String userID) {
        return given().log().all()
                .contentType(ContentType.URLENC)
                .header("Authorization", AuthorizationKeys.getSlackToken())
                .queryParam("channel", channelID)
                .queryParam("user", userID)
                .post(Endpoints.SLACK_CHANNEL + ".kick");
    }

    public Response inviteUserToChannel(String channelID, String userID) {
        return given().log().all()
                .contentType(ContentType.URLENC)
                .header("Authorization", AuthorizationKeys.getSlackToken())
                .queryParam("channel", channelID)
                .queryParam("user", userID)
                .post(Endpoints.SLACK_CHANNEL + ".invite");
    }

    public Response deleteChannel(String channelID) {
        return given().log().all()
                .contentType(ContentType.URLENC)
                .header("Authorization", AuthorizationKeys.getSlackToken())
                .queryParam("channel", channelID)
                .post(Endpoints.SLACK_CHANNEL + ".delete");
    }

    public Response getMessagesHistoryFromChannel(String channelID) {
        return given().log().all()
                .contentType(ContentType.URLENC)
                .header("Authorization", AuthorizationKeys.getSlackToken())
                .queryParam("channel", channelID)
                .get(Endpoints.SLACK_CHANNEL + ".history");
    }

    public List<Map<String, Object>> getMessagesList(String channelID) {
        return getMessagesHistoryFromChannel(channelID).getBody().jsonPath().getList("messages");
    }

    public String getMessageTimestampInChannel(String channelID, String userID, boolean isUser, String textMessage) {
        for (Map<String, Object> message : getMessagesList(channelID)){
            if (message.get("text").equals(textMessage))
                if (isUser) {
                    if (message.containsKey("user") && message.get("user").equals(userID))
                        return message.get("ts").toString();
                }
                else
                    if (message.containsKey("bot_id") && message.get("bot_id").equals(userID))
                        return message.get("ts").toString();
        }
        return null;
    }

    public boolean isMessageInHistory(String channelID, String timestamp) {
        for (Map<String, Object> message : getMessagesList(channelID)){
            if (message.get("ts").toString().equals(timestamp))
                return true;
        }
        return false;
    }

    public String getMessageTextByTimestamp(String channelID, String timestamp) {
        for (Map<String, Object> message : getMessagesList(channelID)) {
            if (message.get("ts").toString().equals(timestamp))
                return message.get("text").toString();
        }
        return null;
    }


    /** USERS */

    public Response inviteUserToWorkspaceByEmail(String channelID, String userEmail) {
        return given().log().all()
                .contentType(ContentType.URLENC)
                .header("Authorization", AuthorizationKeys.getSlackLegacyToken())
                .queryParam("email", userEmail)
                .queryParam("channels", channelID)
                .post(Endpoints.SLACK_USERS + ".admin.invite");
    }

    public List<Map<String, String>> getUsersList() {
        return given().log().all()
                .header("Authorization", AuthorizationKeys.getSlackToken())
                .contentType(ContentType.URLENC)
                .get(Endpoints.SLACK_USERS + ".list").getBody().jsonPath().getList("members");
    }

    public List<String> getUserNames() {
        List<String> users = new ArrayList<>();
        for (Map<String, String> user : getUsersList()){
            users.add(user.get("name"));
        }
        return users;
    }

    public String getUserIDByName(String userName) {
        for (Map<String, String> user : getUsersList())
            if (user.get("name").equals(userName) || user.get("real_name").equals(userName))
                return user.get("id");
        return null;
    }

    /** CHATS */

    private String getUserOrBotToken(boolean isUser){
        if (isUser)
            return AuthorizationKeys.getSlackToken();
        else
            return AuthorizationKeys.getSlackBotToken();
    }

    public Response postMessage(String channelID, String text, boolean isUser) {
        String token = getUserOrBotToken(isUser);
        String username = "";
        if (!isUser)
            username = "restassuredrobot";

        return given().log().all()
                .contentType(ContentType.URLENC)
                .header("Authorization", token)
                .queryParam("channel", channelID)
                .queryParam("text", text)
                .queryParam("as_user", isUser)
                .queryParam("username", username)
                .post(Endpoints.SLACK_CHAT + ".postMessage");
    }

    public Response updateMessage(String channelId, String messageToEditTimestamp, String newMessageText, boolean isUser) {
        String token = getUserOrBotToken(isUser);
        return given().log().all()
                .contentType(ContentType.URLENC)
                .header("Authorization", token)
                .queryParam("channel", channelId)
                .queryParam("ts", messageToEditTimestamp)
                .queryParam("text", newMessageText)
                .queryParam("as_user", isUser)
                .post(Endpoints.SLACK_CHAT + ".update");
    }

    public Response deleteMessageFromChannel(String channelID, String messageTimestamp, boolean isUser) {
        String token = getUserOrBotToken(isUser);

        return given().log().all()
                .contentType(ContentType.URLENC)
                .header("Authorization", token)
                .queryParam("channel", channelID)
                .queryParam("ts", messageTimestamp)
                .queryParam("as_user", isUser)
                .post(Endpoints.SLACK_CHAT + ".delete");
    }

}
