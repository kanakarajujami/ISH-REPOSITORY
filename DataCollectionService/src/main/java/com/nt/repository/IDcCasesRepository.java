package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.DcCasesEntity;

public interface IDcCasesRepository extends JpaRepository<DcCasesEntity, Integer> {

}
