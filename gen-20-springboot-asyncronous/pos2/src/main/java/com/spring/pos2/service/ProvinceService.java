package com.spring.pos2.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.pos2.dto.ProvinceReq;
import com.spring.pos2.util.MappingUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProvinceService {
    private final RestClient restClient;

    public ProvinceService(){
        restClient = RestClient.builder()
                .baseUrl("https://api.rajaongkir.com/starter/province")
                .defaultHeader("key","14ecfa8bfba82059e6af3f0d01a9acbb")
                .build();
    }

    public ProvinceReq getAllData(){
        String jsonResponse = restClient.get()
                .retrieve()
                .body(String.class);
        try{
            ObjectMapper objectMapper = MappingUtil.getObjectMapper();
            ProvinceReq provinceReq = objectMapper.readValue(jsonResponse, ProvinceReq.class);
            return provinceReq;
        } catch (JsonProcessingException e){
            e.printStackTrace();
            return null;
        }
    }


}
