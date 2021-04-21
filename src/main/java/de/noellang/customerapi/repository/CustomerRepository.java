package de.noellang.customerapi.repository;

import de.noellang.customerapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

	Optional<Customer> findByEmail(String email);

}
