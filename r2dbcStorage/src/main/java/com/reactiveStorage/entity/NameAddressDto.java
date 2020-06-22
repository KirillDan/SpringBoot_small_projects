package com.reactiveStorage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NameAddressDto {
	private String name;
	private String address;
}
