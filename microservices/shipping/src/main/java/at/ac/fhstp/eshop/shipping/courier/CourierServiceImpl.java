package at.ac.fhstp.eshop.shipping.courier;

import at.ac.fhstp.eshop.shipping.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;

    public CourierServiceImpl(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @Override
    public Courier getCourierByName(String name) {
        return courierRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Courier with name " + name + " not found"));
    }
}
