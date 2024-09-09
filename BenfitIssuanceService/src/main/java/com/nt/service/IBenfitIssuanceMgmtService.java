package com.nt.service;

import org.springframework.batch.core.JobExecution;

public interface IBenfitIssuanceMgmtService {
    public  JobExecution sendAmountToBenficiries() throws Exception;
}
