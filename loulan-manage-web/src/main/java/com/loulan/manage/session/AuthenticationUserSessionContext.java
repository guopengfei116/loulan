package com.loulan.manage.session;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationUserSessionContext {
    private static Map<String, HttpSession> map = new HashMap();

    public static synchronized void addSession(HttpSession session) {
        if (session != null) {
            map.put(session.getId(), session);
        }
    }

    public static synchronized void delSession(HttpSession session) {
        if (session != null) {
            map.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String session_id) {
        if (session_id == null)
            return null;
        return (HttpSession) map.get(session_id);
    }
}
