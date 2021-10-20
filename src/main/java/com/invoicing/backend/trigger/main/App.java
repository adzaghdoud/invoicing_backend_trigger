package com.invoicing.backend.trigger.main;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.apache.logging.log4j.LogManager;
import org.quartz.CronScheduleBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.invoicing.backend.trigger.hibernate.configuration.AppConfig;
import com.invoicing.backend.trigger.service.CompanyService;

public class App 
{    
	
    public static void main( String[] args ) throws SchedulerException
    {           final org.apache.logging.log4j.Logger log =  LogManager.getLogger(App.class);
               if (System.getProperty("cronseconde")==null  || System.getProperty("cronminutes")==null || System.getProperty("cronheure")==null ) {
      	        log.error("Nombre argument insuffisants ,il faut reseigner les secondes,minutes et heures");
      	        }else {
            	JobDetail job = JobBuilder.newJob(ImportTransaction.class)
      	        .withIdentity("triggerimport").build();      	    	
      	    	Trigger trigger = TriggerBuilder
      	        .newTrigger()
      	        .startNow()
      	        .withIdentity("triggerimport")
      	        .withSchedule(CronScheduleBuilder.cronSchedule( System.getProperty("cronseconde")+" "+System.getProperty("cronminutes")+" "+System.getProperty("cronheure")+" * * ?"))         
      	        .build();
      	    	
      	    	//schedule it
      	    	Scheduler scheduler = new StdSchedulerFactory().getScheduler();
      	    	scheduler.start();
      	    	scheduler.scheduleJob(job, trigger);
      	        }
    
    
    }


}
