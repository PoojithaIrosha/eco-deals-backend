package com.poojithairosha.ecodeals.util;

public interface ApiUrlConstants {

    interface CommonUrlConstants {
        String API_V_1 = "/api/v1";
        String BY_ID = "/{id}";
        String SEARCH = "/search";
        String PAGED = "/paged";
        String BATCH = "/batch";
    }

    interface UserUrlConstants {
        String USERS = "/users";
        String USER_ID = "/{userId}";
    }

    interface AuthUrlConstants {
        String AUTH = "/auth";
        String LOGIN = "/login";
        String REGISTER = "/register";
    }

}
