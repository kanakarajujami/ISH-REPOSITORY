package com.nt.service;



import java.util.Date;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BenfitIssuanceMgmtServiceImpl implements IBenfitIssuanceMgmtService {
    @Autowired	
    private JobLauncher launcher;
    @Autowired
    private Job job;
	@Override
	public JobExecution sendAmountToBenficiries() throws Exception{
		    JobParameter<Date> param=new JobParameter<Date>(new Date(), Date.class);
		    Map<String, JobParameter<?>> map=Map.of("par1",param);
		    JobParameters params=new JobParameters(map);
	        JobExecution execution=launcher.run(job,  params);
	    	return execution;
	}

}
