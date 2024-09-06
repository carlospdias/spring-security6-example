package br.jus.tse.postagem.config_sec;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Autenticacao {

    private ClientRegistrationRepository  clientRegistrationRepository;

    public Autenticacao(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @GetMapping("secured/postagem")
    public String coj(){
        ClientRegistration keycloak = this.clientRegistrationRepository.findByRegistrationId("postagem");
        
        
        System.out.println(keycloak);

        return "secured";
    }
}
