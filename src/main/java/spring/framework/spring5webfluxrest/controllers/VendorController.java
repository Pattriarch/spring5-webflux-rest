package spring.framework.spring5webfluxrest.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.framework.spring5webfluxrest.domain.Vendor;
import spring.framework.spring5webfluxrest.repositories.VendorRepository;

import java.util.Objects;

@RestController
public class VendorController {
    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/api/v1/vendors")
    Flux<Vendor> list() {
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    Mono<Vendor> getById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/vendors")
    public Mono<Void> create(@RequestBody Publisher<Vendor> vendor) {
        return vendorRepository.saveAll(vendor).then();
    }

    @PutMapping("/api/v1/vendors/{id}")
    public Mono<Void> update(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return vendorRepository.save(vendor).then();
    }

    @PatchMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {
        Vendor foundVendor = vendorRepository.findById(id).block();

        if (!Objects.equals(foundVendor.getFirstName(), vendor.getFirstName())) {
            foundVendor.setFirstName(vendor.getFirstName());
            vendorRepository.save(foundVendor);
        }

        if (!Objects.equals(foundVendor.getLastName(), vendor.getLastName())) {
            foundVendor.setLastName(vendor.getLastName());
            vendorRepository.save(foundVendor);
        }

        return Mono.just(foundVendor);
    }
}
