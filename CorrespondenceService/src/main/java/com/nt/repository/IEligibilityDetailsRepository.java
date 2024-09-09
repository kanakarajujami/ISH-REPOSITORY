package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.EligibilityDetailsEntity;

public interface IEligibilityDetailsRepository extends JpaRepository<EligibilityDetailsEntity, Integer> {
   public EligibilityDetailsEntity findByCaseNumber(Integer caseNo);
}
