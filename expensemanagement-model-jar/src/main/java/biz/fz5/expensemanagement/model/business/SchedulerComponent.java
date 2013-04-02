package biz.fz5.expensemanagement.model.business;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author fabiozambelli
 *
 */
public class SchedulerComponent {

	public SchedulerComponent(){
		
		System.out.println("SchedulerComponent started");
		
		try {
			
	        SchedulerFactory sf = new StdSchedulerFactory();
	        Scheduler sched = sf.getScheduler();
	        
	        JobDetail job = newJob(ReceiptConsumerJob.class)
	                .withIdentity("receipt", "consumer")
	                .build();
	        
	        CronTrigger trigger = newTrigger()
	                .withIdentity("trigger", "consumer")
	                .withSchedule(cronSchedule("0 0/1 * * * ?"))
	                .build();
	        
	        Date ft = sched.scheduleJob(job, trigger);
	        System.out.println(job.getKey() + " has been scheduled to run at: " + ft
	                + " and repeat based on expression: "
	                + trigger.getCronExpression());
	        
	        sched.start();
	        
	        System.out.println("Scheduler started");
	        
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
