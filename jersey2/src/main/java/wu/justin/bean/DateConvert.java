package wu.justin.bean;

import java.sql.Timestamp;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import wu.justin.rest2.converter.ISO8601DateConverter.ISO8601DateDeserializer;
import wu.justin.rest2.converter.ISO8601DateConverter.ISO8601DateSerializer;


/** 
 * please see http://wiki.fasterxml.com/JacksonFAQDateHandling 
 * for details
 * */
@JsonPropertyOrder(alphabetic=true)
public class DateConvert {
	
	private java.util.Date utilDate;      // it suppose to be 1467725549246, to but we set format in JacksonObjectMapperProvider, so it is Jul/2016/05T09:33:21-0400
	private java.sql.Date sqlDate;        // this one will be converted into 2016-07-05 , Jackson handle it in special way, Jackson suggest not use it
	private Timestamp timestamp;           // it suppose to be 1467725549246, to but we set format in JacksonObjectMapperProvider, so it is Jul/2016/05T09:33:21-0400
	private java.util.Date sqlInUtilDate;  // this one will be converted into 2016-07-05  , because its true type is java.sql.Date
	private java.util.Date utilDateOnCustomized;  // this one will be converted into 2016-07-05T13:33:21+0000  , because it has customized converter
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MMM-yyyy-dd", timezone="EST")  // Eastern Standard Time UTC-5
	private java.util.Date utilDateOnFormat;  // this one will be converted into Jul-2016-05  , because it has format definition
	
	private Calendar dateOnCalendar;
	
	public DateConvert(){
		utilDate = new java.util.Date();
		sqlDate = new java.sql.Date(utilDate.getTime());
		timestamp = new Timestamp(utilDate.getTime());
		sqlInUtilDate = sqlDate;
		utilDateOnCustomized = new java.util.Date();
		utilDateOnFormat = new java.util.Date();
		dateOnCalendar = Calendar.getInstance();
		
		
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

	@JsonSerialize(using = ISO8601DateSerializer.class)
	public java.util.Date getUtilDateOnCustomized() {
		return utilDateOnCustomized;
	}

	@JsonDeserialize(using = ISO8601DateDeserializer.class)
	public void setUtilDateOnCustomized(java.util.Date utilDateOnCustomized) {
		this.utilDateOnCustomized = utilDateOnCustomized;
	}

	public java.util.Date getUtilDateOnFormat() {
		return utilDateOnFormat;
	}

	public void setUtilDateOnFormat(java.util.Date utilDateOnFormat) {
		this.utilDateOnFormat = utilDateOnFormat;
	}

	public Calendar getDateOnCalendar() {
		return dateOnCalendar;
	}

	public void setDateOnCalendar(Calendar dateOnCalendar) {
		this.dateOnCalendar = dateOnCalendar;
	} 
	

}
