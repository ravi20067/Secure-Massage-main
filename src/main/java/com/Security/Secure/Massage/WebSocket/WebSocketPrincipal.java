package com.Security.Secure.Massage.WebSocket;

import java.security.Principal;

public class WebSocketPrincipal implements Principal {

    private final String email;

    public WebSocketPrincipal(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return email;
    }
}
