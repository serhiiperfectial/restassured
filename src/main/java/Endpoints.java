import io.restassured.*;
import io.restassured.response.Response;

public class Endpoints {

    public static final String NEARBY_PLACE = URLs.PLACE_URL + "/nearbysearch/json?location={lat},{lng}&radius={radius}&key="+AuthorizationKeys.API_KEY;
    //"https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=YOUR_API_KEY\n"


    public String setPlaceType(String placeType){
        return NEARBY_PLACE + "&type=" + placeType;
    }

    public String setPlaceKeyword(String keyword) {
        return NEARBY_PLACE + "&keyword=" + keyword;
    }

}
