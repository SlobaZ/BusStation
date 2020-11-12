package paket.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paket.model.Carrier;
import paket.repository.CarrierRepository;
import paket.service.CarrierService;

@Service
public class JpaCarrierService implements CarrierService{

	@Autowired
	private CarrierRepository carrierRepository;
	
	@Override
	public List<Carrier> findAll() {
		return carrierRepository.findAll();
	}

	@Override
	public Carrier getOne(Long id) {
		return carrierRepository.getOne(id);
	}

	@Override
	public Carrier save(Carrier carrier) {
		return carrierRepository.save(carrier);
	}

	@Override
	public Carrier delete(Long id) {
		Carrier carrier = carrierRepository.getOne(id);
		if(carrier != null) {
			carrierRepository.delete(carrier);
		}
		return carrier;
	}

	@Override
	public Carrier findByName(String name) {
		return carrierRepository.findByName(name);
	}

}
