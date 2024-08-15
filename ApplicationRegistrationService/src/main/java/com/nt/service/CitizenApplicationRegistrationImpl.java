package com.nt.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.nt.bindings.CitizenAppRegistrationInputs;
import com.nt.entity.CitizenAppRegistrationEntity;
import com.nt.exceptions.InvalidSSNException;
import com.nt.repository.IAppllicationRegistrationRepository;

import reactor.core.publisher.Mono;
@Service("citizenService")
public class CitizenApplicationRegistrationImpl implements ICitizenApplicationRegistrationService {
	@Autowired
	private IAppllicationRegistrationRepository citizenRepositoy;
	@Autowired
    private RestTemplate template;
	@Value("${arm.ssa-web.url}")
	private String endpointUrl;
	@Value("${arm.stateName}")
	private String targetState;
	@Autowired
	private WebClient client;
	@Override
	public Integer citizenRegistrationApplication(CitizenAppRegistrationInputs inputs) throws InvalidSSNException {
	      //  String webURL="http://localhost:9090/ssa-web-api/find/{ssn}";
	    //  ResponseEntity<String> response=template.exchange(ssaWebUrl,HttpMethod.GET,null,String.class,inputs.getSsn()) ;
		    //  String stateName=client.get().uri(endpointUrl, inputs.getSsn()).retrieve().bodyToMono(String.class).block();
		  Mono<String> response=client.get().uri(endpointUrl,inputs.getSsn()).retrieve().onStatus(HttpStatus.BAD_REQUEST::equals, res->res.bodyToMono(String.class).map(ex->new InvalidSSNException("invalid ssn"))).bodyToMono(String.class);
		  String stateName=response.block();
	        //get body
	       // String stateName=response.getBody();
	        //verify state name
	        if(stateName.equalsIgnoreCase(targetState)) {
	        	  CitizenAppRegistrationEntity  entity=new CitizenAppRegistrationEntity();
	        	  BeanUtils.copyProperties(inputs, entity);
	        	  entity.setStateName(stateName);
	        	  Integer appId=citizenRepositoy.save(entity).getAppId();
	             return appId;
	        }
		throw new InvalidSSNException("Invalid SSN");
	}

}
