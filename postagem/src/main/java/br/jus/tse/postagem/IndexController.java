package br.jus.tse.postagem;

import java.time.LocalDateTime;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	
	@GetMapping("/")
	public ModelAndView index(@AuthenticationPrincipal OidcUser principal ) {
		ModelAndView securedData = new ModelAndView();
       // Principal principal =(Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal);
        //Map<String, Object> securedData = new HashMap<>();
        if (principal != null) {
            securedData.addObject("user", principal.getName());
            securedData.addObject("claims", principal.getClaims());
        } else {
            securedData.addObject("error", "User not authenticated");
        }

        securedData.addObject("horas", LocalDateTime.now());
        securedData.setViewName("index");
		
		return securedData;
	}
	
	@GetMapping("/publico")
    public ModelAndView publico() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("horas", LocalDateTime.now());
        
        mv.setViewName("public/index");
        
        return mv;
    }
	
	@GetMapping("/administrador")
    public ModelAndView administrador() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("horas", LocalDateTime.now());
        
        mv.setViewName("administrador/index");
        
        return mv;
    }
}
