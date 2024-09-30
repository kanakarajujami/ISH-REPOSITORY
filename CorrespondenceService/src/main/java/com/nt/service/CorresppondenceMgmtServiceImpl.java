package com.nt.service;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.aspectj.weaver.ast.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nt.bindings.COSummary;
import com.nt.entity.CitizenAppRegistrationEntity;
import com.nt.entity.CoTriggersEntity;
import com.nt.entity.DcCasesEntity;
import com.nt.entity.EligibilityDetailsEntity;
import com.nt.repository.CitizenAppRegistrationRepository;
import com.nt.repository.ICoTriggersRepository;
import com.nt.repository.IDcCasesRepository;
import com.nt.repository.IEligibilityDetailsRepository;
import com.nt.utils.EmailUtil;
@Service("coService")
public class CorresppondenceMgmtServiceImpl implements CorrespondenseMgmtService {
	@Autowired
    private ICoTriggersRepository triggerRepo;
	@Autowired
	private IEligibilityDetailsRepository eligibilityRepo;
	@Autowired
	private IDcCasesRepository caseRepo;
	@Autowired
	private CitizenAppRegistrationRepository citizenRepo;
	@Autowired
	private EmailUtil mailUtil;
	   Integer succesTriggers=0;
	   Integer failureTriggers=0;
	
	@Override
	public COSummary processPendingTriggers() {
		
		   EligibilityDetailsEntity eligibilityEntity=null; 
		   CitizenAppRegistrationEntity citizenEntity=null;
		
         //get all pending triggers
		 List<CoTriggersEntity> triggerList=triggerRepo.findByTriggerStatus("pending");
		  COSummary summary=new COSummary();
		  summary.setTotalTriggers(triggerList.size());
		  //process each pending trigger in multi thread environment using executor framework
		  ExecutorService executor=Executors.newFixedThreadPool(10);
		  ExecutorCompletionService<Object> pool=new ExecutorCompletionService<Object>(executor);
		  for(CoTriggersEntity triggerEntity: triggerList) {
			       //call tiggrt process method
			  pool.submit(new Callable<Object>() {
			
				@Override
				public Object call() throws Exception {
				    try {
				    	   processTrigger(triggerEntity);
					       succesTriggers++;
					    }
					       catch(Exception e) {
					    	   failureTriggers++;
					    	   e.printStackTrace();   	 
					       }   
					
					return null;
				}
			});
			  		   
		  }//end of for
		    summary.setSuccessTriggers(succesTriggers);
		    summary.setFailureTriggers(failureTriggers);
				return summary;
	}
	
	  //trigger process method
	  private CitizenAppRegistrationEntity  processTrigger(CoTriggersEntity triggerEntity) throws Exception {
		  // get Eligibility details based on case no  
		  EligibilityDetailsEntity   eligibilityEntity= eligibilityRepo.findByCaseNumber(triggerEntity.getCaseNumber());
		  
		   // get citizen data based on appidti
		  Optional<DcCasesEntity> optCaseEntity=caseRepo.findById(triggerEntity.getCaseNumber());
		  CitizenAppRegistrationEntity citizenEntity=null;
		  if(optCaseEntity.isPresent()) {
			 DcCasesEntity caseEntity=optCaseEntity.get();
			 Optional<CitizenAppRegistrationEntity> optCitizenEntity=citizenRepo.findById(caseEntity.getAppId());
			
			 if(optCaseEntity.isPresent()) {
			         citizenEntity=optCitizenEntity.get();	  
			 }//inner if
			 
		  }//outer if
		      generatePdf(citizenEntity,   eligibilityEntity);
		  return citizenEntity;
		  
	  }
	private void generatePdf(CitizenAppRegistrationEntity citizenEntity,EligibilityDetailsEntity eligiEntity) throws Exception {
		// generate pdf doc having eligibility details
			 //create document object
		Document document=new Document(PageSize.A4);
		//get pdfwriter obj to write to the content in document and file object
		File file=new File(eligiEntity.getCaseNumber()+".pdf");
		FileOutputStream fos=new FileOutputStream(file);
		PdfWriter.getInstance(document, fos);
		document.open();
		  //define font  for the paragraph
		 Font font=FontFactory.getFont(FontFactory.TIMES_BOLD);
		 font.setSize(30);
		 font.setColor(Color.RED);
		 
		 //create the paragraph having content and font-style
		 Paragraph para=new Paragraph("Plan Approval/Deniel Commmunication", font);
		 para.setAlignment(Paragraph.ALIGN_CENTER);
		 //add paragraph to document
	     document.add(para);
	     
	    //display eligibility entity  as pdf table
	     PdfPTable table=new PdfPTable(10);
	     table.setWidthPercentage(80);
	     table.setWidths(new float[]{5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f});
	     table.setSpacingBefore(4.0f);
	     
	   //perpare heading row cells in the pdf table
	     
	     PdfPCell cell=new PdfPCell();
	     cell.setBackgroundColor(Color.gray);
	     cell.setPadding(5);
	     Font cellFont=FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	     cellFont.setColor(Color.BLACK);
	     
	     cell.setPhrase(new Phrase("TraceId",cellFont));
	     table.addCell(cell);
	     cell.setPhrase(new Phrase("CaseNo",cellFont) );
	     table.addCell(cell);
	     cell.setPhrase(new Phrase("HolderName",cellFont));
	     table.addCell(cell);
	     cell.setPhrase(new Phrase("HolderSSN",cellFont));
	     table.addCell(cell);
	     cell.setPhrase(new Phrase("PlanName",cellFont));
	     table.addCell(cell);
	     cell.setPhrase(new Phrase("PlanStatus",cellFont));
	     table.addCell(cell);
	     cell.setPhrase(new Phrase("PlanStartDate",cellFont));
	     table.addCell(cell);
	     cell.setPhrase(new Phrase("PlanEndDate",cellFont));
	     table.addCell(cell);
	     cell.setPhrase(new Phrase("BenfitAmount",cellFont));
	     table.addCell(cell);
	     cell.setPhrase(new Phrase("BankAccNo",cellFont));
	     table.addCell(cell);
	     cell.setPhrase(new Phrase("BankName",cellFont));
	     table.addCell(cell);
	     cell.setPhrase(new Phrase("DenialReason",cellFont));
	     table.addCell(cell);
	     
	    // add data cells to pdftable
	  
	    	 table.addCell(String.valueOf(eligiEntity.getEDTraceId()));
	    	 table.addCell(String.valueOf(eligiEntity.getCaseNumber()));
	    	 table.addCell(eligiEntity.getHolderName());
	    	 table.addCell(String.valueOf(eligiEntity.getHolderSSN()));
	    	 table.addCell(eligiEntity.getPlanName());
	    	 table.addCell(eligiEntity.getPlanStatus());
	    	 table.addCell(String.valueOf(eligiEntity.getPlanStartDate()));
	    	 table.addCell(String.valueOf(eligiEntity.getPlanEndDate()));
	    	 table.addCell(String.valueOf(eligiEntity.getBenfitAmount()));
	    	 table.addCell(String.valueOf(eligiEntity.getBankAccNo()));
	    	 table.addCell(eligiEntity.getBankName());
	    	 table.addCell(eligiEntity.getDenialReason());
	   
	      	//add table to document
	       document.add(table);
	       //close the document
	        document.close();
	        String subject="Plan Approval/Denial mail";
	        String body="Hello Mr/Miss/Mrs."+citizenEntity.getFullName()+"this mail contains complete details plan approvalm or deniel";
	        // send pdf doc to citizen as email
	     mailUtil.sendEmail(citizenEntity.getEmail(),subject,body,file);
	     // store pdf doc in CO_Triggers table and update trigger status is completed
	        updateCoTrigger(eligiEntity.getCaseNumber(),file);
		
		
	}
	private void updateCoTrigger(Integer caseNo,File file) {
		  //check trigger availability
		 CoTriggersEntity triggerEntity=triggerRepo.findByCaseNumber(caseNo);
		 //get byte array point to file
		 byte[] pdfContent=new byte[(int) file.length()];
	    triggerEntity.setCoNoticePdf(pdfContent);
	    triggerEntity.setTriggerStatus("completed");
	    triggerRepo.save(triggerEntity);
	}

}
