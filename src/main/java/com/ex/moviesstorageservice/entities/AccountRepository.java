package com.ex.moviesstorageservice.entities;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>{
    Account findOneByMail(String mail);
}
