package utils;

import java.util.HashMap;
import java.util.Map;

public class UserStore {
    private static final Map<String, String> users = new HashMap<>();
    private static final Map<String, String> userRoles = new HashMap<>();

    static {
        users.put("admin", "admin123");
        users.put("user1", "pass1");

        userRoles.put("admin", "ADMIN");
        userRoles.put("user1", "USER");
    }

    public static boolean isValidUser(String login, String password) {
        return users.containsKey(login) && users.get(login).equals(password);
    }

    public static String getRole(String login) {
        return userRoles.getOrDefault(login, "USER");
    }
}
