package com.nt.service;

import com.nt.bindings.EligibilityDetailsOutput;

public interface IEligibilityDeteminationService {
   public EligibilityDetailsOutput detemineEligibility(Integer caseNumber);
}
