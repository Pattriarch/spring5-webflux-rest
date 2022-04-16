package spring.framework.spring5webfluxrest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.framework.spring5webfluxrest.domain.Vendor;
import spring.framework.spring5webfluxrest.repositories.VendorRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

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
}