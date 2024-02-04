package com.jmant69.csvtopostapi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmant69.model.Customer;

public class CSVToPostAPI {

	public static void main(String[] args) throws URISyntaxException, IOException {

		String path = "/Users/julianmantovani/git/CSVToPostAPI/Customer File.txt";
		String line = "";

		URL url = new URI("http://localhost:8080/customer").toURL();

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));

			while ((line = br.readLine()) != null) {
				String jsonData = convertLineToJSON(line);
				
				HttpURLConnection con = createNewConnection(url);
				
				try (OutputStream os = con.getOutputStream()) {

					os.write(jsonData.getBytes());
					os.flush();
					os.close();
				}
				processPostResponse(con);
			}

			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static HttpURLConnection createNewConnection(URL url) throws IOException, ProtocolException {
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		return con;
	}

	private static String convertLineToJSON(String line) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String[] values = line.split(",");
		Customer customer = new Customer(Long.parseLong(values[0]), values[1], values[2], values[3], values[4],
				values[5], values[6], values[7]);
		String jsonData = mapper.writeValueAsString(customer);
		return jsonData;
	}

	private static void processPostResponse(HttpURLConnection con) throws IOException, UnsupportedEncodingException {
		try (BufferedReader br2 = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br2.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
		}
	}

}
