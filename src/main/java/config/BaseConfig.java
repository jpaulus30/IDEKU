package config;

import io.github.cdimascio.dotenv.Dotenv;

public class BaseConfig {


    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public static String baseURL() {
        String baseUri = dotenv.get("GOREST_BASE_URL");

        if (baseUri == null || baseUri.isEmpty()) {
            throw new RuntimeException(
                    "GOREST_BASE_URL is missing. Check .env file"
            );
        }
        return baseUri;
    }
}