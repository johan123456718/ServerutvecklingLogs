package com.serverutveckling.logs.controller;


import com.serverutveckling.logs.db.DbInterface;
import com.serverutveckling.logs.db.DbManager;
import com.serverutveckling.logs.objects.Users;
import com.serverutveckling.logs.objects.logPost;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

import static org.springframework.http.ResponseEntity.ok;


/**
 * Controller handling REST calls
 */
@Controller

@CrossOrigin(origins = "*")
@RequestMapping(path="/demo", method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST}, produces = { "application/json; charset=utf-8" })
public class RestController {

    @Autowired
    DbInterface DbManager = new DbManager();

    @GetMapping(path="/usernames")
    public @ResponseBody
    ArrayList<Users> getUsernames(@RequestParam String uuid){
        return DbManager.getUsernames(uuid);
    }

    @GetMapping(path="/resultLogs")
    public @ResponseBody Iterable<logPost> getAllResultLogs (@RequestParam String uuid) {
        return DbManager.getLogsByUuid(uuid);
    }

    @PostMapping(path="/addLog")
    public @ResponseBody String addLog(@RequestParam String message, @RequestParam String title, @RequestParam String uuid, @RequestParam String reciever) {
        return DbManager.updateLog(message, title, uuid, reciever);
    }

    @PostMapping(path="/addChart")
    public @ResponseBody String addChart(@RequestParam String uuid, @RequestParam String type, @RequestParam String data){
        return DbManager.updateLogChart(uuid, type, data);
    }


}
