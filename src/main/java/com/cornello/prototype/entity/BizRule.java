package com.cornello.prototype.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CP_BIZRULE")
public class BizRule {

	@Id
	private Long bizRuleSeqNo;

	@Column(name = "BIZRULETYPE")
	private Integer bizRuleType;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "STARTDATE")
	private LocalDate startDate;

	@Column(name = "ENDDATE")
	private LocalDate endDate;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "TIMESTMP")
	private LocalDate timeStmp;

	public BizRule() {
		// empty contructor for JPA
	}

	public Long getBizRuleSeqNo() {
		return bizRuleSeqNo;
	}

	public void setBizRuleSeqNo(Long bizRuleSeqNo) {
		this.bizRuleSeqNo = bizRuleSeqNo;
	}

	public Integer getBizRuleType() {
		return bizRuleType;
	}

	public void setBizRuleType(Integer bizRuleType) {
		this.bizRuleType = bizRuleType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDate getTimeStmp() {
		return timeStmp;
	}

	public void setTimeStmp(LocalDate timeStmp) {
		this.timeStmp = timeStmp;
	}

	@Override
	public String toString() {
		return "BizRule [bizRuleSeqNo=" + bizRuleSeqNo + ", bizRuleType=" + bizRuleType + ", endDate=" + endDate
				+ ", remarks=" + remarks + ", startDate=" + startDate + ", timeStmp=" + timeStmp + ", value=" + value
				+ "]";
	}

}
