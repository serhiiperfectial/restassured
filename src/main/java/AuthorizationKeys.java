import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationKeys {

    private static List<String> getKeysList() {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get("src/main/resources/tokens.txt"));
        } catch (IOException e){
            e.printStackTrace();
        }
        return lines;
    }

    public static String getGoogleKey() {
        for (String line : getKeysList()) {
            if (line.startsWith("GoogleKey"))
                return line.substring(line.indexOf(" ")+1).trim();
        }
        return null;
    }

    public static String getSlackToken() {
        for (String line : getKeysList()) {
            if (line.startsWith("SlackToken"))
                return line.substring(line.indexOf(" ")+1).trim();
        }
        return null;
    }

    public static String getSlackLegecyToken() {
        for (String line : getKeysList()) {
            if (line.startsWith("SlackLegacyToken"))
                return line.substring(line.indexOf(" ")+1).trim();
        }
        return null;
    }
}
