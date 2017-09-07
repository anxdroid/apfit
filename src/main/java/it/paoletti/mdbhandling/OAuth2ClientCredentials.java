package it.paoletti.mdbhandling;
public class OAuth2ClientCredentials {

  /** Value of the "API Key". */
  public static final String API_KEY = "130801181262-lirftvcgge1m3679tio1iaqtnhqlm9rn.apps.googleusercontent.com";

  /** Value of the "API Secret". */
  public static final String API_SECRET = "SpoRvKzJJoXlh-aFBYYD3OK-";

  /** Port in the "Callback URL". */
  public static final int PORT = 8080;

  /** Domain name in the "Callback URL". */
  public static final String DOMAIN = "127.0.0.1";

  public static void errorIfNotSpecified() {
    if (API_KEY.startsWith("Enter ") || API_SECRET.startsWith("Enter ")) {
      System.out.println(
          "Enter API Key and API Secret from http://www.dailymotion.com/profile/developer"
          + " into API_KEY and API_SECRET in " + OAuth2ClientCredentials.class);
      System.exit(1);
    }
  }
}