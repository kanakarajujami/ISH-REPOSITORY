package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.CoTriggersEntity;
import com.nt.entity.EligibilityFDetailsEntity;

public interface ICoTriggersRepository  extends JpaRepository<CoTriggersEntity, Integer>{

}
