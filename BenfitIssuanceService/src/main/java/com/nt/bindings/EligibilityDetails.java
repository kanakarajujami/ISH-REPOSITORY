package com.nt.bindings;
import lombok.Data;

@Data
public class EligibilityDetails {
	private Integer  caseNumber;
	private  String holderName;
	private  Long holderSSN;
	private String planName;
	private String planStatus;
	private Double benfitAmount;
	private Long bankAccNo;
	private String bankName;

}
