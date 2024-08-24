package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.DcEducationEntity;

public interface DcEducationRepositroy extends JpaRepository<DcEducationEntity, Integer> {
   public DcEducationEntity findByCaseNumber(Integer caseNumber);
}
