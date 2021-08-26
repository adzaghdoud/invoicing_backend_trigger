package com.invoicing.backend.trigger.main;



import org.apache.commons.io.IOUtils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.invoicing.backend.trigger.hibernate.configuration.AppConfig;
import com.invoicing.backend.trigger.service.CompanyService;
public class ImportTransaction  implements Job{
	final org.apache.logging.log4j.Logger log =  LogManager.getLogger(this.getClass().getName());
	  public void execute(JobExecutionContext context)
			    throws JobExecutionException {
		   
		     
		    AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		    CompanyService srvcompany = (CompanyService) ctx.getBean("CompanyService");
	
		    for (int i=0 ; i<=srvcompany.getlistcompany().size() ; i++) {
		    	if (srvcompany.getlistcompany().get(i).getBankname().toUpperCase().contentEquals("QONTO")) {
		    		   log.info("*******************************Begin launching trigger for "+srvcompany.getlistcompany().get(i).getRs()+"****************");
		    		  try {
		 				 // regenerate json transactions
		 				 ProcessBuilder processBuilder = new ProcessBuilder("/stor/invoicing/scripts/get_tansactions_qonto.sh",srvcompany.getlistcompany().get(i).getRib(),srvcompany.getlistcompany().get(i).getSlug(),srvcompany.getlistcompany().get(i).getToken(),"/stor/invoicing/in/transactions_"+srvcompany.getlistcompany().get(i).getRs()+".json");
		 				 processBuilder.redirectErrorStream(true);
		 				 Process p = processBuilder.start();
		 				 log.info(new String(IOUtils.toByteArray(p.getInputStream()))); 
		 				 if( p.getErrorStream().available() >0) {
		 				 log.error(new String(IOUtils.toByteArray(p.getErrorStream()))); 
		 			     }
		 				 p.waitFor();
		 			
		 				 
		 			} catch (Exception e) {
		 			log.error(ExceptionUtils.getStackTrace(e));
		 			}		   	    		
		    			try {
		    		    // call invoicing-backend.jar
		    			 ProcessBuilder processBuilder = new ProcessBuilder("java -jar","/stor/invoicing-backend/invoicing-backend.jar","/stor/invoicing/in/transactions_"+srvcompany.getlistcompany().get(i).getRs()+".json",srvcompany.getlistcompany().get(i).getRs());	    	
		    			 processBuilder.redirectErrorStream(true);
		    			 Process p = processBuilder.start();
		    			 log.info(new String(IOUtils.toByteArray(p.getInputStream()))); 
		    			 if( p.getErrorStream().available() >0) {
		    			 log.error(new String(IOUtils.toByteArray(p.getErrorStream()))); 
		    		     }
		    			 p.waitFor();
		    		     			 
		    		} catch (Exception e) {
		    		log.error(ExceptionUtils.getStackTrace(e));
		    		}
		    	
		    			log.info("*******************************End launching trigger for "+srvcompany.getlistcompany().get(i).getRs()+"****************");
		    	}
		    	
		    }
		      
		    ctx.close();
			        
			    }
}
