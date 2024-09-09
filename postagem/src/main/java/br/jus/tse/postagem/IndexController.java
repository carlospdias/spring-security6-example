package br.jus.tse.postagem;

import java.security.Principal;
import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	
	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("horas", LocalDateTime.now());
		
		mv.setViewName("index");
		
		return mv;
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
    /*
    @GetMapping("/")
    public void siei (HttpServletRequest request){
        Principal principal = request.getUserPrincipal();

        System.out.println(principal.getName());
        return ""
    }*/
}
