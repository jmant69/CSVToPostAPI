package com.jmant69.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

	private Long customerRef;
	private String customerName;
	private String addressLine1;
	private String addressLine2;
	private String town;
	private String county;
	private String country;
	private String postCode;
}
