
package br.jus.tse.postagem.config_sec;


import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.nimbusds.jose.util.JSONObjectUtils;

@Configuration
public class SecurityBeans {
    
  
    private ClientRegistration keycloakClientRegistration() {

        ClientRegistration build = ClientRegistration.withRegistrationId("postagem")
                .issuerUri("http://localhost:8080/realms/postagem")
                .clientId("postagem-cli")
                .clientSecret("TEDMvS1cHmQjOkpXsGnSs2wP8576ukpj")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/secured")
                .scope("openid", "profile", "email", "address", "phone")
                .authorizationUri("http://localhost:8080/realms/postagem/protocol/openid-connect/auth")
                .tokenUri("http://localhost:8080/realms/postagem/protocol/openid-connect/token")
                .userInfoUri("http://localhost:8080/realms/postagem/protocol/openid-connect/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri("http://localhost:8080/realms/postagem/protocol/openid-connect/certs")
                .clientName("Cliente Postagem")
                .build();
        return build;
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {

        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach((authority) -> {
                GrantedAuthority mappedAuthority;

                if (authority instanceof OidcUserAuthority) {
                    OidcUserAuthority userAuthority = (OidcUserAuthority) authority;
                    mappedAuthority = new OidcUserAuthority(
                            "OIDC_USER", userAuthority.getIdToken(), userAuthority.getUserInfo());
                } else if (authority instanceof OAuth2UserAuthority) {
                    OAuth2UserAuthority userAuthority = (OAuth2UserAuthority) authority;
                    mappedAuthority = new OAuth2UserAuthority(
                            "OAUTH2_USER", userAuthority.getAttributes());
                } else {
                    mappedAuthority = authority;
                }

                mappedAuthorities.add(mappedAuthority);
            });

            return mappedAuthorities;
        };
    }
    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector() {
        return new SpringOpaqueTokenIntrospector(
                "https://my-auth-server.com/oauth2/introspect", "my-client-id", "my-client-secret");
    }
    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {
            // Delegate to the default implementation for loading a user
            OidcUser oidcUser = delegate.loadUser(userRequest);

            System.out.println(oidcUser.getAttributes());

            System.out.println(oidcUser.getClaims());
            System.out.println(oidcUser.getUserInfo());
            OAuth2AccessToken accessToken = userRequest.getAccessToken();
            Collection<GrantedAuthority> mappedAuthorities = new HashSet<>();
            String generatedToken = accessToken.getTokenValue();

            mappedAuthorities = payloadExtractGrants(generatedToken);

            // TODO
            // 1) Fetch the authority information from the protected resource using accessToken

            // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities

            // 3) Create a copy of oidcUser but use the mappedAuthorities instead
            ClientRegistration.ProviderDetails providerDetails = userRequest.getClientRegistration().getProviderDetails();
            String userNameAttributeName = providerDetails.getUserInfoEndpoint().getUserNameAttributeName();

            if (StringUtils.hasText(userNameAttributeName)) {
                oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), userNameAttributeName);
            } else {
                oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
            }
      
            return oidcUser;
        };
    }

    private Map<String, Object> translate(String payload) {
        try {
            return JSONObjectUtils.parse(payload);
        }
        catch(ParseException pe) {
            pe.printStackTrace();
        }
        return Collections.emptyMap();
    }
    private Collection<GrantedAuthority> payloadExtractGrants(String tokenValue) {
        String[] chunks = tokenValue.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        
        Map<String, Object> claims = translate(payload);
        
        Collection<GrantedAuthority> mappedAuthorities = new KeycloakAuthoritiesConverter().convert(claims);
        return mappedAuthorities;
    }
    
    private class KeycloakAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

        @Override
        public Collection<GrantedAuthority> convert(Jwt jwt) {
            return convert(jwt.getClaims());
        }

        public Collection<GrantedAuthority> convert(Map<String, Object> claims) {
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (String authority : getAuthorities(claims)) {
                if (authority.contains("ADMINISTRADOR")){
                    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));
                }
                if (authority.contains("AUTENTICADO")){
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
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.keycloakClientRegistration());
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(
            ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository(
            OAuth2AuthorizedClientService authorizedClientService) {
        return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResposeClient() {
        DefaultAuthorizationCodeTokenResponseClient client = new DefaultAuthorizationCodeTokenResponseClient();
        RestTemplate restTemplate = new RestTemplate(Arrays.asList(
                new FormHttpMessageConverter(), new OAuth2AccessTokenResponseHttpMessageConverter()));
        CloseableHttpClient requestFactory = HttpClientBuilder.create().build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(requestFactory));
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        client.setRestOperations(restTemplate);
        return client;
    }

    
}
