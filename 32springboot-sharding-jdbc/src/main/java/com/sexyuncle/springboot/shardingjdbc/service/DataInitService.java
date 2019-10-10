package com.sexyuncle.springboot.shardingjdbc.service;

import com.loserico.commons.utils.DateUtils;
import com.loserico.commons.utils.StringUtils;
import com.loserico.io.utils.IOUtils;
import com.loserico.orm.dao.EntityOperations;
import com.peacefish.spring.annotation.PostInitialize;
import com.sexyuncle.springboot.shardingjdbc.entity.GdTicketMapped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>
 * Copyright: (C), 2019 2019-09-25 15:41
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Service
public class DataInitService {

	public static void main(String[] args) throws InterruptedException {
		StringBuilder sb = new StringBuilder();
		// for(int i=0; i<= 1000; i++) {
		// 	String staffNo = ThreadLocalRandom.current().nextLong(999999999)+"";
		// 	for(int j=0; j<= 1000; j++) {
		// 		long ticketId = ThreadLocalRandom.current().nextLong(1000000000);
		// 		sb.append(staffNo).append(",").append(ticketId).append(IOUtils.LINE_SEPARATOR_UNIX);
		// 	}
		// }

		String[] dealSites = new String[]{"310118", "430103", "340104", "310119", "431103"};
		for(int i=0; i<= 1000; i++) {
			String staffNo = ThreadLocalRandom.current().nextLong(999999999) + "";
			for (int j = 0; j <= 1000; j++) {
				//String staffNo = ThreadLocalRandom.current().nextLong(999999999)+"";
				long ticketId = ThreadLocalRandom.current().nextLong(1000000000);
				sb.append(staffNo).append(",")
						.append(ticketId).append(",")
						.append(dealSites[(int) Math.round(Math.random() * 4)]).append(",")
						.append(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
						.append(IOUtils.LINE_SEPARATOR_UNIX);
				Thread.sleep(1);
			}
		}
		IOUtils.write("d://data4.txt", sb.toString());
	}

	@Autowired
	private EntityOperations entityOperations;

	@PostInitialize
	public void initData() {
		GdTicketMapped gdTicketMapped = new GdTicketMapped(StringUtils.uniqueKey(9), ThreadLocalRandom.current().nextLong(1000000000));
	}
}
