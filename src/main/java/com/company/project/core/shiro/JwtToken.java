package com.company.project.core.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JwtToken
 *
 * @author libaogang
 * @since 2019-08-15 20:58
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}