import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.response.Response;

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
        List<Map<String, String>> res =  given().log().all()
                .header("Authorization", AuthorizationKeys.getSlackToken())
                .contentType(ContentType.URLENC)
                .get(Endpoints.SLACK_CHANNEL + ".list").getBody().jsonPath().getList("channels");
        return res;
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

}
