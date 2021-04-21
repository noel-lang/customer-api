package de.noellang.customerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import de.noellang.customerapi.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByEmail(String email);

}
