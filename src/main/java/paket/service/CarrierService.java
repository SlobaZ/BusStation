package paket.service;

import java.util.List;

import paket.model.Carrier;

public interface CarrierService {
	
	List<Carrier> findAll();
	Carrier getOne(Long id);
	Carrier save(Carrier carrier);
	Carrier delete(Long id);
	
	Carrier findByName(String naziv);

}
