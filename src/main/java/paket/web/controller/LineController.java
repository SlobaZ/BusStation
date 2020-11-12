package paket.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import paket.model.Line;
import paket.repository.LineRepository;
import paket.service.LineService;

@Controller
public class LineController {

	@Autowired
	private LineService lineService;

	@Autowired
	private LineRepository lineRepository;
	
	

	@GetMapping("/lineAll")
	public String getAll(Model model) {
		model.addAttribute("line", lineService.findAll());
		return "line";
	}

		
	@RequestMapping(value = "/lines", method = RequestMethod.GET)
	public String searchLines(@RequestParam (required = false) String destination,
										@RequestParam (required = false) String carrierName,
										@RequestParam ( required = false) Double ticketPrice,
										HttpServletRequest request, Model model) {
	    
		int page = 0;
		int size = 5;
	            
	    Page<Line> linePage = null;
	
			if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
				page = Integer.parseInt(request.getParameter("page")) - 1;
			}

			if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
				size = Integer.parseInt(request.getParameter("size"));
			}
		    if (destination != null || carrierName != null || ticketPrice != null) {
		    	linePage = lineService.search(destination, carrierName, ticketPrice, page);
		    }
		    else {
		    	linePage = lineRepository.findAll(PageRequest.of(page, size));
		    }
		    
	    model.addAttribute("lines", linePage);
	    return "line";
	}
	


	
	
	
	

}
