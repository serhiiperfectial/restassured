import com.sun.xml.internal.ws.api.ha.StickyFeature;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.common.assertion.Assertion;
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

    public void registerNewCustomer(String firstName, String userName, String password, String email) {

    }



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
        List<Map<String, String>> channels =  given().log().all()
                .header("Authorization", AuthorizationKeys.getSlackToken())
                .contentType(ContentType.URLENC)
                .get(Endpoints.SLACK_CHANNEL + ".list").getBody().jsonPath().getList("channels");
        return channels;
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
                .queryParam("channel", channelID)
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
                .queryParam("channel", channelID)
                .queryParam("name", newName)
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


    /** USERS */

    public Response inviteUserToWorkspaceByEmail(String channelID, String userEmail) {
        return given().log().all()
                .contentType(ContentType.URLENC)
                .header("Authorization", AuthorizationKeys.getSlackLegecyToken())
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
            if (user.get("name").equals(userName))
                return user.get("id");
        return null;
    }
}
