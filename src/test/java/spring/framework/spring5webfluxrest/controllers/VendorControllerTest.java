package spring.framework.spring5webfluxrest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.framework.spring5webfluxrest.domain.Vendor;
import spring.framework.spring5webfluxrest.repositories.VendorRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void list() {
        given(vendorRepository.findAll()).willReturn(Flux.just(new Vendor(), new Vendor()));

        webTestClient.get().uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        given(vendorRepository.findById("someId")).willReturn(Mono.just(new Vendor()));

        webTestClient.get().uri("/api/v1/vendors/someId")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    void create() {
        given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorToCreate = Mono.just(Vendor.builder().firstName("Name").lastName("LastName").build());

        webTestClient.post().uri("/api/v1/vendors")
                .body(vendorToCreate, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void update() {
        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToUpdate = Mono.just(Vendor.builder().firstName("Name").lastName("LastName").build());

        webTestClient.put().uri("/api/v1/vendors/someId")
                .body(vendorToUpdate, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

    }
}