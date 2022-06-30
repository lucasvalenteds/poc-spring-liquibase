package com.example.country;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountryRepository extends CrudRepository<Country, UUID> {

    Optional<Country> findByCode(String code);

    default Country findByCodeOrThrow(String code) throws CountryNotFoundException {
        return findByCode(code)
                .orElseThrow(() -> new CountryNotFoundException(code));
    }
}
