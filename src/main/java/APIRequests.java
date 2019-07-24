import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.path.json.*;

import java.util.List;

import static io.restassured.RestAssured.given;

public class APIRequests {


    public Response findLocation(String lat, String lon, String radius){
        return RestAssured.given().contentType(ContentType.JSON).
                pathParam("lat", lat).
                pathParam("lng", lon).
                pathParam("radius", radius).
                get(Endpoints.NEARBY_PLACE);
    }

    public boolean isSearchedNameFound(String name, Response response){
        return response.getBody().jsonPath().getList("results.name").contains(name);
    }
}
