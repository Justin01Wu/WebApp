package wu.justin.bean;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MyTimestamp {
	
	protected Timestamp runDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")	
	private Date jsonFormatDate = new Date();
	
	@JsonProperty("processEndTime")
	public void setRunDate(Timestamp runDate) {
		this.runDate = runDate;
	}

	@JsonProperty("processEndTime")
	public Timestamp getRunDate() {
		return runDate;
	}


}
