package br.jus.tse.postagem.config_sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Import(SecurityBeans.class)
@EnableWebSecurity
public class SecurityConfig {
    private final KeycloakLogoutHandler keycloakLogoutHandler;

    public SecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
        this.keycloakLogoutHandler = keycloakLogoutHandler;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SecurityBeans securityBeans = new SecurityBeans();

        http.authorizeHttpRequests((auth->{

            auth.requestMatchers("/secured/**","/public/**","/assets/**","/invalid-session").permitAll();
            auth.requestMatchers("/administrador").hasAuthority("AUTENTICADO");

            auth.anyRequest().authenticated();

        }))
        .sessionManagement((sess)->{
            
            sess.invalidSessionUrl("/invalid-session");
            sess.sessionFixation(fix->fix.changeSessionId());
            sess.maximumSessions(1);
           
        })
        .oauth2Client((auth)->{
                    auth.authorizationCodeGrant(codeg -> {
                       codeg.accessTokenResponseClient(securityBeans.accessTokenResposeClient());
                    });
                })
            .oauth2Login((auth)->{
                auth.successHandler(new OAuth2LoginSuccessHandler());

                auth.authorizationEndpoint(authEndpoint ->{
                    authEndpoint.baseUri("/secured");
                    System.out.println("O que vem aqui?");
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
   
                }));
        }).logout(logout -> logout.logoutUrl("/logout")
                .addLogoutHandler(keycloakLogoutHandler)
                .invalidateHttpSession(true)  
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/"));
     

        return http.build();
    }
   
    
}
