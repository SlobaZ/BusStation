package paket.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import paket.service.LineService;
import paket.service.CarrierService;
import paket.service.ReservationService;
import paket.model.Line;
import paket.model.Carrier;
import paket.model.Reservation;
import paket.model.User;
import paket.repository.ReservationRepository;
import paket.repository.UserRepository;
import paket.security.UserService;

@Controller
public class AdminController {  
	
	@Autowired
	private CarrierService carrierService;
	
	@Autowired
	private LineService lineService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private ReservationRepository reservationRepository;
		
	
	

	@GetMapping(value="/admin/addcarrier")
    public ModelAndView newCarrier(){
        ModelAndView modelAndView = new ModelAndView();
        Carrier carrier = new Carrier(); 
        modelAndView.addObject("carrier", carrier);
        modelAndView.setViewName("/admin/carrier-add");
        return modelAndView;
    }
	
    @PostMapping(value = "/admin/addcarrier")
    public ModelAndView createNewCarrier(@Valid Carrier carrier, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Carrier prevoznikExists = carrierService.findByName(carrier.getName()); 
        if (prevoznikExists != null) {
            bindingResult.rejectValue("name", "error.name",  "There is already a carrier added with the name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/admin/carrier-add");
        } 
        else {
        	carrierService.save(carrier);
            modelAndView.addObject("successMessage", "Carrier has been add successfully");
            modelAndView.addObject("carrier", new Carrier());
            modelAndView.setViewName("/admin/carrier-add");
        }
        return modelAndView;
    }

    
    @GetMapping("/admin/deletecarrier/{id}")
    private String deleteCarrier(@PathVariable("id") Long id){
    	Carrier  carrier = carrierService.getOne(id);
    	if(carrier != null){
    		carrierService.delete(id);
        	
        }else{
            System.err.println("not found");
        }
        return "redirect:/carriers";
    }
    

    @GetMapping(path = {"/admin/editcarrier/{id}"})
    private String addFormCarrier(@PathVariable("id") Long id, Model model){
        if(id != null){
            model.addAttribute("carrier", carrierService.getOne(id) );
        }
        else{
        	return null;
        }
        return "/admin/carrier-edit";
    }
    
    
    @PostMapping("/admin/editCarrier")
    private String insertOrUpdateCarrier(@Valid Carrier carrier, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
        	return "/admin/carrier-edit";
        	} 
        else{
        	Carrier editCarrier = carrierService.getOne(carrier.getId());
            if(editCarrier !=null){
            	editCarrier.setName(carrier.getName());
            	editCarrier.setAddress(carrier.getAddress());
            	editCarrier.setPib(carrier.getPib());
            	carrierService.save(editCarrier);
            }
        }
        return "redirect:/carriers";

    }
    
    
    
    
    @GetMapping(value="/admin/addline")
    public ModelAndView newLine(){
        ModelAndView modelAndView = new ModelAndView();
        Line line = new Line();
        modelAndView.addObject("line", line);
        List<Carrier> carriers = carrierService.findAll();
        modelAndView.addObject("carriers", carriers);
        modelAndView.setViewName("/admin/line-add");
        return modelAndView;
    }
	
    @PostMapping(value = "/admin/addline")
    public ModelAndView createNewLine(@Valid Line line,  BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Line lineExists = lineService.alreadyExists(line.getDestination(),line.getCarrier().getId(),line.getDepartureTime()); 
        if (lineExists != null) {
            bindingResult.rejectValue("destination", "error.destination",  "There is already a line added");
        }
        if (bindingResult.hasErrors()) {
        	List<Carrier> carriers = carrierService.findAll();
            modelAndView.addObject("carriers", carriers);
            modelAndView.setViewName("/admin/line-add");
        } 
        else {
        	lineService.save(line);
            modelAndView.addObject("successMessage", "Line has been add successfully");
            modelAndView.addObject("line", new Line());
            modelAndView.setViewName("/admin/line-add");

        }
        return modelAndView;
    }

    
    @GetMapping("/admin/deleteline/{id}")
    private String deleteLine(@PathVariable("id") Long id){
    	Line  line = lineService.getOne(id);
        if(line != null){
        	lineService.delete(id);
        	
        }else{
            System.err.println("not found");
        }
        return "redirect:/lines";
    }
    

    @GetMapping(path = {"/admin/editline/{id}"})
    private String addFormLinija(@PathVariable("id") Long id, Model model){
        if(id != null){
            model.addAttribute("line", lineService.getOne(id) );
            List<Carrier> carriers = carrierService.findAll();
            model.addAttribute("carriers", carriers);
        }
        else{
        	return null;
        }
        return "/admin/line-edit";
    }
    
    
    @PostMapping("/admin/editLine")
    private String insertOrUpdateLine(@Valid Line line, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
        	return "/admin/line-edit";
        	} 
        else{
        	Line editLine = lineService.getOne(line.getId());
            if(editLine !=null){
            	editLine.setNumberOfSeats(line.getNumberOfSeats());
            	editLine.setTicketPrice(line.getTicketPrice());
            	editLine.setDepartureTime(line.getDepartureTime());
            	editLine.setDestination(line.getDestination());
            	editLine.setCarrier(line.getCarrier());
            	lineService.save(editLine);
            }
        }
        return "redirect:/lines";
    }
    
    
      
    
        
	@GetMapping("/admin/usersAll")
	public String listaUsers(Model model) {
		model.addAttribute("users", userService.findAll());		
		return "/admin/user";
	}
	
	
	
	@RequestMapping(value = "/admin/users", method = RequestMethod.GET)
	public String allAndSearchUsers (@RequestParam (required = false) String username,
										@RequestParam (required = false) String city,
										HttpServletRequest request, Model model) {
	    
		int page = 0;
		int size = 5;
	    
	    Page<User> userPage = null;
	
			if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
				page = Integer.parseInt(request.getParameter("page")) - 1;
			}

			if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
				size = Integer.parseInt(request.getParameter("size"));
			}
		    if (username != null || city != null) {
		    	userPage = userService.search(username, city, page);
		    }
		    else {
		    	userPage = userRepository.findAll(PageRequest.of(page, size));
		    }
		    
	    model.addAttribute("users", userPage);
	    return "/admin/user";
	}
	

	
	 @GetMapping("/admin/deleteuser/{id}")
	    private String deleteUser(@PathVariable("id") Long id){
	    	User  user = userService.getOne(id);
	        if(user != null){
	        	userService.delete(id);
	        }else{
	            System.err.println("not found");
	        }
	        return "redirect:/admin/users";
	    }
	    

	    @GetMapping(path = {"/admin/edituser/{id}"})
	    private String addFormUser(@PathVariable("id") Long id, Model model){
	        if(id != null){
	            model.addAttribute("user", userService.getOne(id) );
	        }
	        else{
	        	return null;
	        }
	        return "/admin/user-edit";
	    }
	    
	    
	    @PostMapping("/admin/editUser")
	    private String insertOrUpdateUser(@Valid User user, BindingResult bindingResult){
	        if (bindingResult.hasErrors()) {
	        	return "/admin/user-edit";
	        	} 
	        else{
	        	User editUser = userService.getOne(user.getId());
	            if(editUser !=null){
	            	editUser.setUsername(user.getUsername());
	            	editUser.setFirstname(user.getFirstname());
	            	editUser.setLastname(user.getLastname());
	            	editUser.setEmail(user.getEmail());
	            	editUser.setPassword(user.getPassword());
	            	editUser.setCity(user.getCity());
	            	editUser.setAddress(user.getAddress());
	            	editUser.setPhone(user.getPhone());
	            	userService.saveUser(editUser);
	            }
	        }
	        return "redirect:/admin/users";
	    }
	
	

	    
	     
    
	    @RequestMapping(value = "/admin/reservations", method = RequestMethod.GET)
		public String allAndSearchReservations(@RequestParam(value = "username",required=false) String username, 
										  @RequestParam(value = "destination",required=false) String destination,
										  HttpServletRequest request, Model model) {
		    
	    	    	
			int page = 0;
			int size = 5;

			 model.addAttribute("users", userService.findAll());
			
		    Page<Reservation> reservationPage = null;
		
				if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
					page = Integer.parseInt(request.getParameter("page")) - 1;
				}

				if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
					size = Integer.parseInt(request.getParameter("size"));
				}
				if(username!=null || destination!=null ) {
					
					reservationPage = reservationService.search(username, destination, page);
			    }
			    else {
			    	reservationPage = reservationRepository.findAll(PageRequest.of(page, size));
			    }
			    
		    model.addAttribute("reservations", reservationPage);
		    return "admin/reservation-all";
		}

	    
	    
	    
	    
	    
	    @GetMapping("/admin/deletereservation/{id}")
	    private String deleteReservation(@PathVariable("id") Long id){
	    	Reservation  reservation = reservationService.getOne(id);
	        if(reservation != null){
	        	reservationService.delete(id);
	        }else{
	            System.err.println("not found");
	        }
	        return "redirect:/admin/reservations";
	    }
	    
	
	    @GetMapping(path = {"/admin/editreservation/{id}"})
	    private String addFormReservation(@PathVariable("id") Long id, Model model){
	        if(id != null){
	            model.addAttribute("reservation", reservationService.getOne(id) );
	        }
	        else{
	        	return null;
	        }
	        return "/admin/reservation-edit";
	    }
	    
	    
	    @PostMapping("/admin/editReservation")
	    private String insertOrUpdateReservation(@Valid Reservation reservation, BindingResult bindingResult){
	        if (bindingResult.hasErrors()) {
	        	return "/admin/reservation-edit";
	        	} 
	        else{
	        	Reservation editReservation = reservationService.getOne(reservation.getId());
	            if(editReservation !=null){
	            	editReservation.setUser(reservation.getUser());
	            	reservationService.save(editReservation);
	            }
	        }
	        return "redirect:/admin/reservations";
	    }
	
	    
	    
	    
	    
	
    
}