package wu.justin.bean;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DateConvert2 {
	
	protected Timestamp runDate;
	
	@JsonProperty("processEndTime")
	public void setRunDate(Timestamp runDate) {
		this.runDate = runDate;
	}

	@JsonProperty("processEndTime")
	public Timestamp getRunDate() {
		return runDate;
	}
}
