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
		 
			     // 1-genreate json transactions file
		    AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		    CompanyService srvcompany = (CompanyService) ctx.getBean("CompanyService");
		    for (int i=0 ; i<=srvcompany.getlistcompany().size() ; i++) {
		    	if (srvcompany.getlistcompany().get(i).getBankname().toUpperCase().contentEquals("QONTO")) {
		    		
		    		  try {
		 				 
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
		    			 ProcessBuilder processBuilder = new ProcessBuilder("/stor/invoicing/scripts/run_jar_import_qonto.sh","/stor/invoicing-backend/invoicing-backend.jar","/stor/invoicing/in/transactions_"+srvcompany.getlistcompany().get(i).getRs()+".json",srvcompany.getlistcompany().get(i).getRs());	    	
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
		    	
		    	
		    	}
		    	
		    }
		  
		    ctx.close();
			        
			    }
}
