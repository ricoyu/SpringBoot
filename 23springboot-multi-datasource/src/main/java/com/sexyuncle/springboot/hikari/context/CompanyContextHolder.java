package com.sexyuncle.springboot.hikari.context;

import java.util.Objects;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.sexyuncle.springboot.hikari.enums.Company;

public class CompanyContextHolder {

	private static final ThreadLocal<Company> CONTEXT_HOLDER = new TransmittableThreadLocal<>();
	
	public static Company getCompany() {
		return CONTEXT_HOLDER.get();
	}
	
	public static void setCompany(Company company) {
		Objects.requireNonNull(company, "company cannot be null");
		CONTEXT_HOLDER.set(company);
	}
	
	public static void clear() {
		CONTEXT_HOLDER.remove();
	}
}
