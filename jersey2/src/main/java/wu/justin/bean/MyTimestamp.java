package wu.justin.bean;

import java.sql.Timestamp;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class MyTimestamp {
	
	protected Timestamp runDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate localDate;
	
	@JsonProperty("processEndTime")
	public void setRunDate(Timestamp runDate) {
		this.runDate = runDate;
	}

	@JsonProperty("processEndTime")
	public Timestamp getRunDate() {
		return runDate;
	}

	@JsonSerialize(using = LocalDateSerializer.class)
	public LocalDate getLocalDate() {
		return localDate;
	}

	@JsonDeserialize(using = LocalDateDeserializer.class)
	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}
}
