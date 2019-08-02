public class Endpoints {

    public static final String NEARBY_PLACE_PATH_PARAM = URLs.PLACE_URL + "/nearbysearch/json?location={location}&radius={radius}&key={key}";
    public static final String NEARBY_PLACE_QUERRY_PARAM = URLs.PLACE_URL + "/nearbysearch/json";   //?location={lat},{lng}&radius={radius}&key="+AuthorizationKeys.API_KEY;
    public static final String GOOGLE_ADD_PLACE = URLs.PLACE_URL + "/add/json";
    //"https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=YOUR_API_KEY\n"

    public static final String DEMOQA_REGISTER_USER = URLs.DEMOQA_USER + "/register";

    /** ----------------- */
    public static final String SLACK_CHANNEL = URLs.SLACK_URL + "/channels";
    public static final String SLACK_USERS = URLs.SLACK_URL + "/users";
    public static final String SLACK_CHAT = URLs.SLACK_URL + "/chat";
    public static final String SLACK_APPS = URLs.SLACK_URL + "/apps";
    public static final String SLACK_BOTS = URLs.SLACK_URL + "/bots";




    public String setPlaceType(String placeType){
        return NEARBY_PLACE_PATH_PARAM + "&type=" + placeType;
    }

    public String setPlaceKeyword(String keyword) {
        return NEARBY_PLACE_PATH_PARAM + "&keyword=" + keyword;
    }

}
