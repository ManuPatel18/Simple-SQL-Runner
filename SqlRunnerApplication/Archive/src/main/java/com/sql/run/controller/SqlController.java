package com.sql.run.controller;

import com.sql.run.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/sql")
@CrossOrigin(origins = "http://localhost:3000") // React frontend
public class SqlController {

    @Autowired
    private SqlService sqlService;

    @PostMapping("/execute")
    public List<Map<String, Object>> executeQuery(@RequestBody Map<String, String> payload) {
        String query = payload.get("query");
        return sqlService.executeQuery(query);
    }

    @GetMapping("/tables")
    public List<String> getTables() {
        return sqlService.getTables();
    }

    @GetMapping("/table/{tableName}")
    public Map<String, Object> getTableInfo(@PathVariable String tableName) {
        return sqlService.getTableInfo(tableName);
    }
}
