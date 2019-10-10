package com.sexyuncle.springboot.auartz.service;

import static org.quartz.JobKey.jobKey;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sexyuncle.springboot.auartz.model.JobDescriptor;

@Service
@Transactional
public class EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private Scheduler scheduler;

	public JobDescriptor createJob(String group, JobDescriptor descriptor) {
		descriptor.setGroup(group);
		JobDetail jobDetail = descriptor.buildJobDetail();
		Set<Trigger> triggersForJob = descriptor.buildTriggers();
		logger.info("About to save job with key - {}", jobDetail.getKey());
		try {
			scheduler.scheduleJob(jobDetail, triggersForJob, false);
			logger.info("Job with key - {} saved sucessfully", jobDetail.getKey());
		} catch (SchedulerException e) {
			logger.error("Could not save job with key - {} due to error - {}", jobDetail.getKey(),
					e.getLocalizedMessage());
			throw new IllegalArgumentException(e.getLocalizedMessage());
		}
		return descriptor;
	}

	@Transactional(readOnly = true)
	public Optional<JobDescriptor> findJob(String group, String name) {
		// @formatter:off
		try {
			JobDetail jobDetail = scheduler.getJobDetail(jobKey(name, group));
			if (Objects.nonNull(jobDetail))
				return Optional.of(
						JobDescriptor.buildDescriptor(jobDetail,
								scheduler.getTriggersOfJob(jobKey(name, group))));
		} catch (SchedulerException e) {
			logger.error("Could not find job with key - {}.{} due to error - {}", group, name, e.getLocalizedMessage());
		}
		logger.warn("Could not find job with key - {}.{}", group, name);
		return Optional.empty();
	}

	public void updateJob(String group, String name, JobDescriptor descriptor) {
		try {
			JobDetail oldJobDetail = scheduler.getJobDetail(jobKey(name, group));
			if (Objects.nonNull(oldJobDetail)) {
				JobDataMap jobDataMap = oldJobDetail.getJobDataMap();
				jobDataMap.put("subject", descriptor.getSubject());
				jobDataMap.put("messageBody", descriptor.getMessageBody());
				jobDataMap.put("to", descriptor.getTo());
				jobDataMap.put("cc", descriptor.getCc());
				jobDataMap.put("bcc", descriptor.getBcc());
				JobBuilder jb = oldJobDetail.getJobBuilder();
				JobDetail newJobDetail = jb.usingJobData(jobDataMap).storeDurably().build();
				scheduler.addJob(newJobDetail, true);
				logger.info("Updated job with key - {}", newJobDetail.getKey());
				return;
			}
			logger.warn("Could not find job with key - {}.{} to update", group, name);
		} catch (SchedulerException e) {
			logger.error("Could not find job with key - {}.{} to update due to error - {}", group, name,
					e.getLocalizedMessage());
		}
	}

	public void deleteJob(String group, String name) {
		try {
			scheduler.deleteJob(jobKey(name, group));
			logger.info("Deleted job with key - {}.{}", group, name);
		} catch (SchedulerException e) {
			logger.error("Could not delete job with key - {}.{} due to error - {}", group, name,
					e.getLocalizedMessage());
		}
	}

	public void pauseJob(String group, String name) {
		try {
			scheduler.pauseJob(jobKey(name, group));
			logger.info("Paused job with key - {}.{}", group, name);
		} catch (SchedulerException e) {
			logger.error("Could not pause job with key - {}.{} due to error - {}", group, name,
					e.getLocalizedMessage());
		}
	}

	public void resumeJob(String group, String name) {
		try {
			scheduler.resumeJob(jobKey(name, group));
			logger.info("Resumed job with key - {}.{}", group, name);
		} catch (SchedulerException e) {
			logger.error("Could not resume job with key - {}.{} due to error - {}", group, name,
					e.getLocalizedMessage());
		}
	}
}