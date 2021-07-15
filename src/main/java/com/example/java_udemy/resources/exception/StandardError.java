package com.example.java_udemy.resources.exception;

import java.io.Serializable;




public class StandardError implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private Integer status;
	private String mensagemError;
	private long timeStamp;
	public StandardError(Integer notFound, String mensagemError, long timeStamp) {
		super();
		this.status = notFound;
		this.mensagemError = mensagemError;
		this.timeStamp = timeStamp;
	}
	public Integer getStatusHTTP() {
		return status;
	}
	public void setStatusHTTP(Integer status) {
		this.status = status;
	}
	public String getMensagemError() {
		return mensagemError;
	}
	public void setMensagemError(String mensagemError) {
		this.mensagemError = mensagemError;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
