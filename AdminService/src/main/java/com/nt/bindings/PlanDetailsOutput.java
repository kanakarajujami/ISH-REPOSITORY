package com.nt.bindings;
import java.time.LocalDate;
import lombok.Data;
 @Data
public class PlanDetailsOutput {
	    private Integer planId;
	    private String planName;
		private LocalDate planStartDate;
		private LocalDate planEndDate;
		private String description;
		private Integer planCategoryId;
		private String activeSW;
}
