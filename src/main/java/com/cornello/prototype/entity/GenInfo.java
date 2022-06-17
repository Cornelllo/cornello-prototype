package com.cornello.prototype.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "CP_GEN_INFO")
public class GenInfo {
	
	@Id
	@Column(name = "REFSEQNO")
	private Integer refSeqNo;
	
	@Column(name = "PARENTCODE")
	private Integer parentCode;
	
	@Column(name = "SEQUENCE")
	private Integer sequence;
	
	@Column(name = "REFDESC")
	private String refDesc;
	
	@Column(name = "GRKEY")
	private String grKey;
	
	@Column(name = "REFCODE")
	private String refCode;
	
	@Column(name = "TIMESTMP")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date timeStmp;

	public GenInfo() {
		super();
	}

	public Integer getRefSeqNo() {
		return refSeqNo;
	}

	public void setRefSeqNo(Integer refSeqNo) {
		this.refSeqNo = refSeqNo;
	}

	public Integer getParentCode() {
		return parentCode;
	}

	public void setParentCode(Integer parentCode) {
		this.parentCode = parentCode;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getRefDesc() {
		return refDesc;
	}

	public void setRefDesc(String refDesc) {
		this.refDesc = refDesc;
	}

	public String getGrKey() {
		return grKey;
	}

	public void setGrKey(String grKey) {
		this.grKey = grKey;
	}

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public Date getTimeStmp() {
		return timeStmp;
	}

	public void setTimeStmp(Date timeStmp) {
		this.timeStmp = timeStmp;
	}

    @Override
    public String toString() {
        return "GenInfo [grKey=" + grKey + ", parentCode=" + parentCode + ", refCode=" + refCode + ", refDesc="
                + refDesc + ", refSeqNo=" + refSeqNo + ", sequence=" + sequence + ", timeStmp=" + timeStmp + "]";
    }

}
