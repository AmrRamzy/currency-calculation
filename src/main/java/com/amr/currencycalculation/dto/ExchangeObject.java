package com.amr.currencycalculation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeObject {

	private Integer id;

	private String from;

	private String to;

	private Double conversionRate;

	private Integer port;

	private Double amount;

}