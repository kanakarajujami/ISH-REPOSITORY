package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.DcIncomeEntity;

public interface DcIncomeRepository extends JpaRepository<DcIncomeEntity, Integer> {
    public DcIncomeEntity findBycaseNumber(Integer caseNumber);
}
