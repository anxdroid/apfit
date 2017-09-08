package it.paoletti.mdbhandling;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.fitness.Fitness;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

public class MDBOpener {
	final String PASSWD_DB = "ilink@2012";

	/** Directory to store user credentials. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".store/google_fit_sample");

	/**
	 * Global instance of the {@link DataStoreFactory}. The best practice is to make
	 * it a single globally shared instance across your application.
	 */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** OAuth 2 scope. */
	private static final String SCOPE = "read";

	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** Global instance of the JSON factory. */
	static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private static final String TOKEN_SERVER_URL = "https://api.dailymotion.com/oauth/token";
	private static final String AUTHORIZATION_SERVER_URL = "https://api.dailymotion.com/oauth/authorize";

	private static final String APPLICATION_NAME = "Test Fitness";

	static String access_token = "xxxx";
	static String REFRESH_TOKEN = "yyyyy";

	/** Authorizes the installed application to access user's protected data. */
	private static Credential authorize() throws Exception {
		OAuth2ClientCredentials.errorIfNotSpecified();
		// set up authorization code flow
		AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(),
				HTTP_TRANSPORT, JSON_FACTORY, new GenericUrl(TOKEN_SERVER_URL),
				new ClientParametersAuthentication(OAuth2ClientCredentials.API_KEY, OAuth2ClientCredentials.API_SECRET),
				OAuth2ClientCredentials.API_KEY, AUTHORIZATION_SERVER_URL).setScopes(Arrays.asList(SCOPE))
						.setDataStoreFactory(DATA_STORE_FACTORY).build();
		// authorize
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost(OAuth2ClientCredentials.DOMAIN)
				.setPort(OAuth2ClientCredentials.PORT).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	private static Credential authorize2() {
		try {

			GoogleCredential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)
					.setJsonFactory(JSON_FACTORY)
					.setClientSecrets(OAuth2ClientCredentials.API_KEY, OAuth2ClientCredentials.API_SECRET).build();
			credential.setAccessToken(access_token);
			credential.setRefreshToken(REFRESH_TOKEN);
			// GoogleCredential
			/*
			 * Analytics analytics = Analytics.builder(HTTP_TRANSPORT, JSON_FACTORY)
			 * .setApplicationName(APPLICATION_NAME) .setHttpRequestInitializer(credential)
			 * .build();
			 * 
			 * Accounts accounts = analytics.management().accounts().list().execute();
			 */
			return credential;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Credential generateCredentialWithUserApprovedToken() throws IOException, GeneralSecurityException {
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		InputStreamReader inputStreamReader = new InputStreamReader(jsonFileResourceForClient.getInputStream());
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, inputStreamReader);
		return new GoogleCredential.Builder().setTransport(httpTransport).setJsonFactory(jsonFactory)
				.setClientSecrets(clientSecrets).build().setRefreshToken(REFRESH_TOKEN);
	}

	private static void run(HttpRequestFactory requestFactory) throws IOException {
	}

	private static void openDB() {
		Table table = null;
		try {
			table = DatabaseBuilder.open(new File("Beurer.mdb")).getTable("ScaleMeasurement");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Row row : table) {
			// System.out.println("Column 'MeasurementDate' has value: " +
			// row.get("MeasurementDate"));
			Measurement meas = new Measurement(row);
			System.out.println(meas);
		}
	}

	public static void main(String[] args) {
		openDB();
		try {
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
			final Credential credential = authorize();

			Fitness service = new Fitness.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
					.setApplicationName(APPLICATION_NAME).build();

			HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
				@Override
				public void initialize(HttpRequest request) throws IOException {
					credential.initialize(request);
					request.setParser(new JsonObjectParser(JSON_FACTORY));
				}
			});
			// run(requestFactory);
			// Success!
			return;
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
