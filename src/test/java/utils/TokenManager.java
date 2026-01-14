package utils;

import io.github.cdimascio.dotenv.Dotenv;

public class TokenManager {

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public static String getToken() {
        String token = dotenv.get("GOREST_TOKEN");

        if (token == null || token.isEmpty()) {
            throw new RuntimeException(
                    "GOREST_TOKEN is missing. Please check .env or environment variable."
            );
        }
        return token;
    }
}