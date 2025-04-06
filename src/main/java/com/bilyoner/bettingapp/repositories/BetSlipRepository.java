package com.bilyoner.bettingapp.repositories;

import com.bilyoner.bettingapp.entities.BetSlipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetSlipRepository extends JpaRepository<BetSlipEntity, Long> {

}
