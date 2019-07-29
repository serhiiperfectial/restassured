import io.restassured.*;
import io.restassured.response.Response;

public class Endpoints {

    public static final String NEARBY_PLACE_PATH_PARAM = URLs.PLACE_URL + "/nearbysearch/json?location={location}&radius={radius}&key={key}";
    public static final String NEARBY_PLACE_QUERRY_PARAM = URLs.PLACE_URL + "/nearbysearch/json";   //?location={lat},{lng}&radius={radius}&key="+AuthorizationKeys.API_KEY;
    //"https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=YOUR_API_KEY\n"

    public static final String DEMOQA_REGISTER_USER = URLs.DEMOQA_USER + "/register";

    //** ----------------- *//
    public static final String SLACK_CHANNEL = URLs.SLACK_URL + "/channels";
    public static final String SLACK_USERS = URLs.SLACK_URL + "/users";




    public String setPlaceType(String placeType){
        return NEARBY_PLACE_PATH_PARAM + "&type=" + placeType;
    }

    public String setPlaceKeyword(String keyword) {
        return NEARBY_PLACE_PATH_PARAM + "&keyword=" + keyword;
    }

}
