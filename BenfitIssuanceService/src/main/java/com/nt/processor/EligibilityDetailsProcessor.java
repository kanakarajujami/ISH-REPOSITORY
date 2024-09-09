package com.nt.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.nt.bindings.EligibilityDetails;
import com.nt.entity.EligibilityDetailsEntity;
@Component
public class EligibilityDetailsProcessor implements ItemProcessor<EligibilityDetailsEntity, EligibilityDetails> {

	@Override
	public EligibilityDetails process(EligibilityDetailsEntity item) throws Exception {
		    if(item.getPlanStatus().equalsIgnoreCase("Approved")) {
		    	   EligibilityDetails eligiOut=new EligibilityDetails();
		    	   //convert entity object to binding object
		    	   BeanUtils.copyProperties(item, eligiOut);
		    	   return eligiOut;
		    }
		return null;
	}

}
