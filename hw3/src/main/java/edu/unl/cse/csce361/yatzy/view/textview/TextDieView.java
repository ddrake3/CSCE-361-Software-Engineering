package edu.unl.cse.csce361.yatzy.view.textview;

import edu.unl.cse.csce361.yatzy.Game;
import edu.unl.cse.csce361.yatzy.view.AbstractDieView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * The die view for displaying on a text-based console.
 */
public class TextDieView extends AbstractDieView {

	public static final String dieFacesFile = "d" + Game.NUMBER_OF_DIE_SIDES + ".json";

    public static final String[] faces;
    private static final String plainBackground;
    private static final String highlightedBackground;

    static {
        JSONObject dieFacesJson = null;
        String path = TextDieView.class.getPackage().toString().substring("package ".length()).replace('.', '/');
        try (InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(
                TextDieView.class.getClassLoader().getResourceAsStream(path + "/" + dieFacesFile)))) {
            dieFacesJson = (JSONObject) new JSONParser().parse(inputStreamReader);
        } catch (NullPointerException nullPointerException) {
            System.err.println("Could not find " + dieFacesFile + ". Terminating.");
            System.exit(1);
        } catch (IOException ioException) {
            System.err.println("Error loading " + dieFacesFile + ". Terminating.");
            System.exit(1);
        } catch (ParseException e) {
            System.err.println("Parse exception encountered while parsing " + dieFacesFile + ". Terminating.");
            System.exit(1);
        }
        plainBackground = String.format((String) dieFacesJson.get("plainBackground"));
        highlightedBackground = String.format((String) dieFacesJson.get("highlightedBackground"));
        JSONArray unformattedFaces = (JSONArray) dieFacesJson.get("faces");
        //noinspection unchecked
        faces = (String[]) unformattedFaces.stream().map(face -> String.format((String) face)).toArray(String[]::new);
    }

    private static final int HEIGHT = plainBackground.split(System.lineSeparator()).length;
    private static final int WIDTH = plainBackground.split(System.lineSeparator())[0].length();
    private static final int VERTICAL_POSITION = HEIGHT / 2;
    private static final int HORIZONTAL_POSITION = WIDTH / 2;
    private final StringBox stringBox;

    public TextDieView() {
        super();
        stringBox = new StringBox(HEIGHT, WIDTH);
        stringBox.placeString(faces[getValue()], StringBox.Vertical.CENTER, VERTICAL_POSITION,
                StringBox.Horizontal.CENTER, HORIZONTAL_POSITION);
    }

    int getHeight() {
        return HEIGHT;
    }

    @Override
    public String toString() {
        String face = faces[getValue()];
        String background = isHighlighted() ? highlightedBackground : plainBackground;
        stringBox
                .placeString(background, StringBox.Vertical.CENTER, VERTICAL_POSITION,
                        StringBox.Horizontal.CENTER, HORIZONTAL_POSITION)
                .placeString(face, StringBox.Vertical.CENTER, VERTICAL_POSITION,
                        StringBox.Horizontal.CENTER, HORIZONTAL_POSITION);
        return stringBox.toString();
    }
}
