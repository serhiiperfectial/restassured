import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.path.json.*;

import java.util.List;

import static io.restassured.RestAssured.given;

public class APIRequests {


    public Response findLocation(String lat, String lng, String radius){
        return RestAssured.given().contentType(ContentType.JSON).
                queryParam("location", lat + "," + lng).
                queryParam("radius", radius).
                queryParam("key", AuthorizationKeys.API_KEY).
                get(Endpoints.NEARBY_PLACE_QUERRY_PARAM);
    }

    public Response findLocation2(String lat, String lng, String radius){
        return RestAssured.given().contentType(ContentType.JSON).
                pathParam("location", lat + "," + lng).
                pathParam("radius", radius).
                pathParam("key", AuthorizationKeys.API_KEY).
                get(Endpoints.NEARBY_PLACE_PATH_PARAM, "location", "radius", "key");
    }

    public boolean isSearchedNameFound(String name, Response response){
        return response.getBody().jsonPath().getList("results.name").contains(name);
    }

    public void registerNewCustomer(String firstName, String userName, String password, String email) {

    }



    public Response createNewChannel(String channelName){
        return given().log().all()
                .header("Authorization", AuthorizationKeys.SLACK_OAUTH_TOKEN)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "\"name\": \""+ channelName + "\",\n" +
                        "\"validate\": true\n" +
                        "}")
                .post(Endpoints.SLACK_CHANNEL + ".create");
    }

}
