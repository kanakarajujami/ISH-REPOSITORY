package com.nt.service;

import com.nt.bindings.CitizenAppRegistrationInputs;
import com.nt.exceptions.InvalidSSNException;

public interface ICitizenApplicationRegistrationService {
	public Integer citizenRegistrationApplication(CitizenAppRegistrationInputs inputs) throws InvalidSSNException;

}
