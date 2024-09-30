package com.nt.bindings;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;


import lombok.Data;

@Data
public class ChildrenInputs {
 public Integer caseNumber;
 @JsonFormat(pattern = "yyyy-MM-dd")
 @JsonSerialize(using = LocalDateSerializer.class)
 @JsonDeserialize(using = LocalDateDeserializer.class)
 public LocalDate dob;
 public Long ssn;
}
