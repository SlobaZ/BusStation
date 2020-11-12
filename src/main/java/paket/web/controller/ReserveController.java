package paket.web.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import paket.model.Reservation;
import paket.model.User;
import paket.security.UserService;
import paket.service.LineService;


@Controller
public class ReserveController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LineService lineService;

    
    @GetMapping("/user/reserve/{id}")
    private String reserve(@PathVariable("id") Long id, Model model, Principal principal){
    	User user =  userService.checkByEmail(principal.getName());
    	Reservation reservation = lineService.reserve(id,user);
    	if(reservation==null) {
			return null;
		}
		model.addAttribute("reservation", reservation);
	    model.addAttribute("messageToTheUser",  user.getUsername()  + " / " + " " + user.getFirstname() + " " + user.getLastname()   );
        return "/user/result";
    }
    
    
    
    
    
}
