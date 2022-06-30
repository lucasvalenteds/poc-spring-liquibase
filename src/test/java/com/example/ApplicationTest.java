package com.example;

import com.example.country.CountryNotFoundException;
import com.example.country.CountryRepository;
import com.example.customer.Customer;
import com.example.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
class ApplicationTest {

    @Container
    private static final PostgreSQLContainer<?> CONTAINER =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres"));

    @DynamicPropertySource
    private static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", CONTAINER::getUsername);
        registry.add("spring.datasource.password", CONTAINER::getPassword);
    }

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void creatingCustomerFromKnownCountry() {
        final var country = countryRepository.findByCodeOrThrow("US");

        final var customer = customerRepository.save(
                Customer.builder()
                        .name("John Smith")
                        .country(country)
                        .build()
        );

        assertThat(customer.getId())
                .isNotNull();
        assertThat(customerRepository.findAll())
                .contains(customer);
        assertThat(customer.getCountry())
                .isEqualTo(country);
    }

    @Test
    void throwsWhenCountryCodeDoesNotExist() {
        final var code = "CH";

        final var exception = assertThrows(
                CountryNotFoundException.class,
                () -> countryRepository.findByCodeOrThrow(code)
        );

        assertEquals("None country found with code CH", exception.getMessage());
        assertNull(exception.getCause());
    }
}
