package com.serverutveckling.logs.db;


import com.serverutveckling.logs.objects.Users;
import com.serverutveckling.logs.objects.logPost;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Interface declaring methods used for handling communication with a database
 */
@Component
public interface DbInterface {

    public ArrayList<Users> getUsernames(String uuid);

    public Iterable<logPost> getAllLogs();

    public Iterable<logPost> getLogsByUuid(String uuid);

    String updateLog(String message, String title, String uuid, String recipient_uuid);

    String updateLogChart(String uuid, String type, String data);

}
