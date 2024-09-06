package br.jus.tse.postagem.config_sec;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Import(SecurityBeans.class)
@EnableWebSecurity
public class SecurityConfig {
        

    private GrantedAuthority grantedAuthority;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SecurityBeans securityBeans = new SecurityBeans();

        http        
        .authorizeHttpRequests((auth->{
            
            auth.requestMatchers("/secured/**","/public/**","/assets/**").permitAll();
            auth.requestMatchers("/administrador").hasAuthority("AUTENTICADO");

            auth.anyRequest().authenticated();
            
            
        }))
        .sessionManagement(session -> session.sessionFixation((sessionFixation) -> sessionFixation.newSession()) 
                                             .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                             .maximumSessions(1)
                )
        
        .oauth2Client((auth)->{
                    auth.authorizationCodeGrant(codeg -> {
                       codeg.accessTokenResponseClient(securityBeans.accessTokenResposeClient());
                    });
                })
            .oauth2Login((auth)->{
                auth.authorizationEndpoint(authEndpoint ->{
                    authEndpoint.baseUri("/secured");
                    //System.out.println("O que vem aqui?");
                });
    
                auth.redirectionEndpoint(redirection ->{
                    redirection.baseUri("/secured");
                    System.out.println("Aqui se faz o redirecionamento");
                });
    
                auth.tokenEndpoint((token)->{
                    System.out.println("Aqui eu tenho que pegar o token");
                });

                auth.userInfoEndpoint((userInfo ->{
                    System.out.println("Aqui se pegam os dados do usuário");
                    userInfo.oidcUserService(securityBeans.oidcUserService());
                    //userInfo.userAuthoritiesMapper(userAuthoritiesMapper());
                    System.out.println("Aqui se pegam os dados do usuário XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    
                }));
        });
        http.oauth2ResourceServer((oauth2)->{
            oauth2.opaqueToken(Customizer.withDefaults());
        });


        return http.build();
    }
    
    
    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            GrantedAuthority g = new SimpleGrantedAuthority("AUTORIZADO");
            mappedAuthorities.add(g);
            GrantedAuthority g2 = new SimpleGrantedAuthority("ADMNISTRADOR");
            mappedAuthorities.add(g2);

           /* authorities.forEach(authority -> {
                if (OidcUserAuthority.class.isInstance(authority)) {
                    OidcUserAuthority oidcUserAuthority = (OidcUserAuthority)authority;

                    OidcIdToken idToken = oidcUserAuthority.getIdToken();
                    OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();

                    // Map the claims found in idToken and/or userInfo
                    // to one or more GrantedAuthority's and add it to mappedAuthorities

                } else if (OAuth2UserAuthority.class.isInstance(authority)) {
                    OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)authority;

                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

                    // Map the attributes found in userAttributes
                    // to one or more GrantedAuthority's and add it to mappedAuthorities

                }
            });*/

            return mappedAuthorities;
        };
    }
}
