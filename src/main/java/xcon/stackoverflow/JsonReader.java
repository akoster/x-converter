package xcon.stackoverflow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class JsonReader {

    public static void main(String[] args) throws IOException {
        JsonReader jsonReader = new JsonReader();
        jsonReader.read("http://xenzet.com/ds/ds.php?");
    }

    public void read(String url) throws IOException {
        URLConnection urlConnection = getUrlConnection(url);
        String json;
        try (InputStream inputStream = urlConnection.getInputStream()) {
            json = readContent(inputStream);
        }
        System.out.println("JSON: " + json);
    }

    private String readContent(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        return sb.toString();
    }

    private URLConnection getUrlConnection(String url) throws IOException {
        URLConnection urlConnection = new URL(url).openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        return urlConnection;
    }
}
