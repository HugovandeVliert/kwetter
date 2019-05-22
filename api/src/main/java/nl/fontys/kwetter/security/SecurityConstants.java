package nl.fontys.kwetter.security;

class SecurityConstants {
    static final String SECRET = "secret";
    static final long EXPIRATION_TIME = 864_000_000; // 10 days
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/api/users";
    static final String LOG_IN_URL = "/api/users/login";
}