package com.techforb.challenge.repository;

import com.techforb.challenge.entity.Loan;
import com.techforb.challenge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findAllByUser(User user);
}
