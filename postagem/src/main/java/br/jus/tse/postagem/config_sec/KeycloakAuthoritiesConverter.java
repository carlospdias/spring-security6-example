package br.jus.tse.postagem.config_sec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

class KeycloakAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return convert(jwt.getClaims());
    }

    public Collection<GrantedAuthority> convert(Map<String, Object> claims) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : getAuthorities(claims)) {
            if (authority.contains("ADMINISTRADOR")) {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));
            }
            if (authority.contains("AUTENTICADO")) {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_AUTENTICADO"));

            }

        }
        return grantedAuthorities;
    }

    private Collection<String> getAuthorities(Map<String, Object> claims) {
        Object realm_access = claims.get("realm_access");
        if (realm_access instanceof Map) {
            Map<String, Object> map = castAuthoritiesToMap(realm_access);
            Object roles = map.get("roles");
            if (roles instanceof Collection) {
                return castAuthoritiesToCollection(roles);
            }
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> castAuthoritiesToMap(Object authorities) {
        return (Map<String, Object>) authorities;
    }

    @SuppressWarnings("unchecked")
    private Collection<String> castAuthoritiesToCollection(Object authorities) {
        return (Collection<String>) authorities;
    }
}
