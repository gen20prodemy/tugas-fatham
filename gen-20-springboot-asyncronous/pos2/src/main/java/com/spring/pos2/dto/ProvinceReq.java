package com.spring.pos2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.security.PublicKey;

@Data
public class ProvinceReq {
    private RajaOngkirProvince rajaOngkirProvince;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class RajaOngkirProvince{
    private Object result;
}
