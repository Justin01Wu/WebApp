package wu.justin.bean;

import java.sql.Timestamp;

import wu.justin.rest2.ISO8601ShortDateConverter.ISO8601ShortDateSerializer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/** 
 * please see http://wiki.fasterxml.com/JacksonFAQDateHandling 
 * for details
 * */

public class DateConvert {
	
	private java.util.Date utilDate;      // it suppose to be 1467654248281, to but we set format in JacksonObjectMapperProvider, so it is 2016/07/04  
	private java.sql.Date sqlDate;        // this one will be converted into 2016-07-04 , Jackson handle it in special way, Jackson suggest not use it
	private Timestamp timestamp;           // it suppose to be 1467654248281, to but we set format in JacksonObjectMapperProvider, so it is 2016/07/04  
	private java.util.Date sqlInUtilDate;  // this one will be converted into 2016-07-04  , because it true type is java.sql.Date
	private java.util.Date utilDateOnCustimized;  // this one will be converted into 2016-07-04  , because it has customized converter
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MMM-yyyy-dd", timezone="CET")
	private java.util.Date utilDateOnFormat;  // this one will be converted into Jul-2016-04  , because it has format definition 
	
	public DateConvert(){
		utilDate = new java.util.Date();
		sqlDate = new java.sql.Date(utilDate.getTime());
		timestamp = new Timestamp(utilDate.getTime());
		sqlInUtilDate = sqlDate;
		utilDateOnCustimized = new java.util.Date();
		utilDateOnFormat = new java.util.Date();
		
		
	}
	
	public java.util.Date getUtilDate() {
		return utilDate;
	}
	public void setUtilDate(java.util.Date utilDate) {
		this.utilDate = utilDate;
	}
	public java.sql.Date getSqlDate() {
		return sqlDate;
	}
	public void setSqlDate(java.sql.Date sqlDate) {
		this.sqlDate = sqlDate;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public java.util.Date getSqlInUtilDate() {
		return sqlInUtilDate;
	}
	public void setSqlInUtilDate(java.util.Date sqlInUtilDate) {
		this.sqlInUtilDate = sqlInUtilDate;
	}

	@JsonSerialize(using = ISO8601ShortDateSerializer.class)
	public java.util.Date getUtilDateOnCustimized() {
		return utilDateOnCustimized;
	}

	public void setUtilDateOnCustimized(java.util.Date utilDateOnCustimized) {
		this.utilDateOnCustimized = utilDateOnCustimized;
	}

	public java.util.Date getUtilDateOnFormat() {
		return utilDateOnFormat;
	}

	public void setUtilDateOnFormat(java.util.Date utilDateOnFormat) {
		this.utilDateOnFormat = utilDateOnFormat;
	} 
	

}
