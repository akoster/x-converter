package xcon.example.templatemethod;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class UrlDocumentService extends DocumentService {

    private static final String UTF_8 = "UTF-8";

    @Override
    List<Document> listDocuments(String url) {
        List<Document> documents = new ArrayList<>();
        readContent(url).ifPresent(content -> documents.add(new Document(url, content)));
        return documents;
    }

    private Optional<String> readContent(String url) {
        try {
            return Optional.of(new Scanner(new URL(url).openStream(), UTF_8).useDelimiter("\\A").next());
        } catch (IOException e) {
            System.err.println("Error reading URL: " + url);
            return Optional.empty();
        }
    }

    @Override
    List<String> listChildren(String url) {
        List<String> children = new ArrayList<>();
        readContent(url).ifPresent(content -> extractUrls(content, children));
        return children;
    }

    private void extractUrls(String content, List<String> children) {
        // URL extracting code from Sun example
        String lowerCaseContent = content.toLowerCase();
        int index = 0;
        while ((index = lowerCaseContent.indexOf("<a", index)) != -1) {
            if ((index = lowerCaseContent.indexOf("href", index)) == -1) {
                break;
            }
            if ((index = lowerCaseContent.indexOf("=", index)) == -1) {
                break;
            }
            index++;
            String remaining = content.substring(index);
            StringTokenizer st = new StringTokenizer(remaining, "\t\n\r\">#");
            String strLink = st.nextToken();
            if (strLink.startsWith("javascript:")) {
                continue;
            }
            URL urlLink;
            try {
                urlLink = new URL(strLink);
            } catch (MalformedURLException e) {
                continue;
            }
            // only look at http links
            String protocol = urlLink.getProtocol();
            if (protocol.compareTo("http") != 0 && protocol.compareTo("https") != 0) {
                continue;
            }
            children.add(urlLink.toString());
        }
    }
}
