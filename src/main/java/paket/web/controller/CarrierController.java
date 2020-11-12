package paket.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import paket.service.CarrierService;

@Controller
public class CarrierController {

	@Autowired
	private CarrierService carrierService;


	@GetMapping("/carriers")
	public String getAll(Model model) {
		model.addAttribute("carriers", carrierService.findAll());
		return "carrier";
	}

		
	
	
	
	
	

}
