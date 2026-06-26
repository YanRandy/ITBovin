package com.bovin.itBovin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/debug")
public class DebugController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/dette")
    public List<Map<String, Object>> debugDette() {
        String sql = "SELECT * FROM v_dette_fournisseur";
        return jdbcTemplate.queryForList(sql);
    }
}