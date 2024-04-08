package com.spring.pos2.controller;


import com.spring.pos2.dto.ProvinceReq;
import com.spring.pos2.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rajaongkir")
public class RajaOngkirController {

    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/province")
    public ProvinceReq getAll(){
        return provinceService.getAllData();
    }
}
