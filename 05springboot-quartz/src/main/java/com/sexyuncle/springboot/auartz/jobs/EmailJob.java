package com.sexyuncle.springboot.auartz.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailJob implements Job {
	private static final Logger logger = LoggerFactory.getLogger(EmailJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Job triggered to send emails");
		/*
		 * The JobDataMap can be used to hold any amount of (serializable) data
		 * objects which you wish to have made available to the job instance when it
		 * executes. JobDataMap is an implementation of the Java Map interface, and
		 * has some added convenience methods for storing and retrieving data of
		 * primitive types. You can retrieve the JobDataMap from the
		 * JobExecutionContext that is stored as part of the JobDetail or Trigger. The
		 * JobDataMap that is found on the JobExecutionContext during Job execution
		 * serves as a convenience. It is a merge of the JobDataMap found on the
		 * JobDetail and the one found on the Trigger, with the values in the latter
		 * overriding any same-named values in the former.
		 */
		JobDataMap map = context.getMergedJobDataMap();
		sendEmail(map);
		logger.info("Job completed");
	}

	@SuppressWarnings("unchecked")
	private void sendEmail(JobDataMap map) {
		logger.info("======== Email sent =============");
	}
}