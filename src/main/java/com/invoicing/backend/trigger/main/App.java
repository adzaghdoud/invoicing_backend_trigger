package com.invoicing.backend.trigger.main;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class App 
{
    public static void main( String[] args ) throws SchedulerException
    {
      	JobDetail job = JobBuilder.newJob(ImportTransaction.class)
      	        .withIdentity("triggerimport").build();

      	    	
      	    	Trigger trigger = TriggerBuilder
      	        .newTrigger()
      	        .startNow()
      	        .withIdentity("triggerimport")
      	        .withSchedule(CronScheduleBuilder.cronSchedule("0 10 18 * * ?"))            
      	        .build();
      	    	
      	    	//schedule it
      	    	Scheduler scheduler = new StdSchedulerFactory().getScheduler();
      	    	scheduler.start();
      	    	scheduler.scheduleJob(job, trigger);
    
    
    
    }


}
