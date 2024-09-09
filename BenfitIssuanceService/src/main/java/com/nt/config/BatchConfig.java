package com.nt.config;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.PlatformTransactionManager;

import com.nt.bindings.EligibilityDetails;
import com.nt.entity.EligibilityDetailsEntity;
import com.nt.processor.EligibilityDetailsProcessor;
import com.nt.repository.IEligibilityDetailsRepository;

@Configuration("batchConfig")
public class BatchConfig {
	 @Autowired
	 private IEligibilityDetailsRepository eligiRepo;
	 @Autowired
	 private EligibilityDetailsProcessor processor;
	 @Bean("eligi-reader")
     public ItemReader<EligibilityDetailsEntity> createReader(){
    	 return new RepositoryItemReaderBuilder<EligibilityDetailsEntity>()
    			                .name("eligi-reader")
    			                .repository(eligiRepo)
    			                .sorts(Map.of("caseNumber",Direction.ASC))
    			                .methodName("findAll")
      			                .build();	 
     }//end of reader
	 @Bean("eligi-writer")
	 public  ItemWriter<EligibilityDetails> createWriter(){
		  return new FlatFileItemWriterBuilder<EligibilityDetails>()
				                  .name("eligi-writer")
				                  .resource( new FileSystemResource("benficieries_list.csv"))
				                  .lineSeparator("\r\n")
				                  .delimited().delimiter(",")
				                  .names("caseNumber","holderName","holderSSN","planName","planStatus","benfitAmount","bankAccNo","bankName")
				                  .build();	 
	 }//end of writer
	 
	 @Bean("step1")
	 public Step createStep(JobRepository repository,PlatformTransactionManager transactionMgmr) {
		             return new StepBuilder("step1", repository) 
		            		             .<EligibilityDetailsEntity,EligibilityDetails>chunk(3,transactionMgmr)
		            		             .reader(createReader())
		            		             .processor(processor)
		            		             .writer(createWriter())
		            		             .build();
	 }//end of step 
	 @Bean("job1")
	public Job createJob(JobRepository repository,Step step1) {
		    return new  JobBuilder("job1",repository)
		    		               .incrementer(new RunIdIncrementer())
		    		               .start(step1)
		    		               .build();
	}
	  
}
