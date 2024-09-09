package com.nt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nt.bindings.PlanDetailsInputs;
import com.nt.bindings.PlanDetailsOutput;
import com.nt.entity.PlanCategoryEntity;
import com.nt.entity.PlanDetailsEntity;
import com.nt.repository.IPlanCategoryRepository;
import com.nt.repository.IPlanDetailsRepository;
@Service
public class AdminMgmtServiceImpl implements IAdminMgmtService {
	@Autowired
    private IPlanDetailsRepository planRepo;
	@Autowired
	private IPlanCategoryRepository categoryRepo;
	@Override
	public String registerPlan(PlanDetailsInputs planInputs) {
		  //convert binding obj to entity obj
		  PlanDetailsEntity planEntity=new PlanDetailsEntity();
		  BeanUtils.copyProperties(planInputs, planEntity);
		  Integer planId=planRepo.save(planEntity).getPlanId();
		return "plan details are registered with id value:"+planId;
	}

	@Override
	public Map<Object, String> getAllPlanCategories() {
		    List<PlanCategoryEntity> listCategories=categoryRepo.findAll();
		    Map<Object,String> categoryInfo=new HashMap<Object, String>();
		     listCategories.forEach(planCategory->{
		    	 categoryInfo.put(planCategory.getPlanCategoryId(), planCategory.getCategoryName());
		     }); 
		return  categoryInfo;
	}

	@Override
	public List<PlanDetailsOutput> getAllPlanDetails() {
		 List<PlanDetailsEntity> listPlanEntities=planRepo.findAll();
		 List<PlanDetailsOutput> listPlanData=new ArrayList<PlanDetailsOutput>();
		 listPlanEntities.forEach(plan->{
			 PlanDetailsOutput planOut=new PlanDetailsOutput();
			 BeanUtils.copyProperties(plan, planOut);
			 listPlanData.add(planOut);
		 });
		return  listPlanData;
	}

	@Override
	public PlanDetailsOutput showPlanById(Integer planId) {
	     Optional<PlanDetailsEntity> optPlanEntity=planRepo.findById(planId);
	     PlanDetailsOutput planOut=new PlanDetailsOutput();
	     if(optPlanEntity.isPresent()) {
	        PlanDetailsEntity planEntity=optPlanEntity.get();
	        BeanUtils.copyProperties(planEntity, planOut);
	    	    return  planOut;
	     }else {
	    	throw new IllegalArgumentException("plan id not found");
	     }
	
	}

	@Override
	public String updatePlan(PlanDetailsOutput planOut) {
		Optional<PlanDetailsEntity> optPlanEntity=planRepo.findById(planOut.getPlanId());
	     if(optPlanEntity.isPresent()) {
	    	PlanDetailsEntity planEntity=optPlanEntity.get();
	    	BeanUtils.copyProperties(planOut, planEntity);
	    	 planRepo.save(planEntity);
	    	 return "plan Details are updated"; 
	     } 
	        throw new IllegalArgumentException(" plan id not found to update");
	    
	}

	@Override
	public String changePlanStatusById(Integer planId, String status) {
		Optional<PlanDetailsEntity> optPlanEntity=planRepo.findById(planId);
		if(optPlanEntity.isPresent()) {
	      PlanDetailsEntity planEntity=optPlanEntity.get();
	      planEntity.setActiveSW(status);
	      planRepo.save(planEntity);
	      return "Plan status changed";
		}
		   throw new IllegalArgumentException("plan id not found to update status");
	}

}
