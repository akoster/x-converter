package xcon.pilot;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.text.html.HTMLEditorKit;

public class HtmlTextExtractor {

	public static void main(String[] args) {

		HTMLEditorKit.Parser parser = new ParserGetter().getParser();
		HTMLEditorKit.ParserCallback callback = new HTMLEditorKit.ParserCallback() {
			public void handleText(char[] data, int pos) {
				System.out.println(String.valueOf(data));
			}
		};
		try {
			URL u = new URL("http://www.adriaankoster.nl");
			InputStream in = u.openStream();
			InputStreamReader reader = new InputStreamReader(in);
			parser.parse(reader, callback, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

@SuppressWarnings("serial")
class ParserGetter extends HTMLEditorKit {
	public HTMLEditorKit.Parser getParser() {
		return super.getParser();
	}
}