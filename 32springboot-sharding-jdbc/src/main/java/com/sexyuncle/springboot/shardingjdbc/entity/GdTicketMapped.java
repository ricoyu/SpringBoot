package com.sexyuncle.springboot.shardingjdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>
 * Copyright: (C), 2019 2019-09-25 15:46
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Entity
@Table(name = "gd_ticket_mapped")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GdTicketMapped {

	@Column(name = "staff_no")
	private String staffNo;

	private Long ticketId;

}
