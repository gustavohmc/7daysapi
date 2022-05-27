import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		String apiKey = "key";
		URI apiIMDB = URI.create("https://imdb-api.com/en/API/Top250TVs/" + apiKey);
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(apiIMDB).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		String json = response.body();
		
		String[] tvShows = parseJson(json);
		
		List<String> titles = parseItem(tvShows, "title");
		titles.forEach(System.out::println);
	}
	
	private static String[] parseJson(String json) {
		String trimmedJSON = StringUtils.substringBetween(json,"[", "]");
		String[] results = trimmedJSON.split("},");
		return results;
	}
	
	private static List<String> parseItem(String[] shows, String item){
		List<String> results = new ArrayList<>();
		String pattern = item + "\":\"";
		for(int i=0;i<shows.length;i++) {
			results.add(StringUtils.substringBetween(shows[i], pattern, "\""));			
		}
		return results;
	}

}
