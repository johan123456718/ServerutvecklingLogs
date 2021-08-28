package com.serverutveckling.logs.db;


import com.serverutveckling.logs.objects.Users;
import com.serverutveckling.logs.objects.logPost;
import com.serverutveckling.logs.repositories.LogRepository;
import com.serverutveckling.logs.repositories.userRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implements DbInterface to use a MySQL database
 */
@Component
public class DbManager implements DbInterface {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private userRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    public DbManager(){

    }

    /**
     * Gets all logs
     * @return
     */
    @Override
    public Iterable<logPost> getAllLogs() {
        ArrayList<logPost> logPosts = (ArrayList<logPost>) logRepository.findAll();
        Optional<Users> currentUser;
        Optional<Users> currentSender;
        for (logPost post : logPosts
             ) {
            currentUser = userRepository.findByUUID(post.getUser_UUID());
            post.setUser_UUID(currentUser.get().getUsername());
            currentSender = userRepository.findByUUID(post.getRecipient_UUID());
            post.setRecipient_UUID(currentSender.get().getUsername());
        }
        return logPosts;
    }

    /**
     * Gets the log with the correspondant UUID
     * @param uuid
     * @return
     */
    @Override
    public Iterable<logPost> getLogsByUuid(String uuid) {
        Iterable<logPost> logPosts = logRepository.findByReciever(uuid);
        Optional<Users> currentUser;
        Optional<Users> currentSender;

        for (logPost post : logPosts
        ) {
            currentUser = userRepository.findByUUID(post.getUser_UUID());
            post.setUser_UUID(currentUser.get().getUsername());
            currentSender = userRepository.findByUUID(post.getRecipient_UUID());
            post.setRecipient_UUID(currentSender.get().getUsername());
        }
        return logPosts;
    }


    /**
     * Gets all usernames except callers
     * @param uuid
     * @return
     */
    @Override
    public ArrayList<Users> getUsernames(String uuid) {
        Iterable<Users> userList = userRepository.findAll();

        ArrayList<Users> usernameList = new ArrayList<>();
        usernameList.addAll((ArrayList) userList);

        for(int i = 0; i < usernameList.size(); i++){
            if(usernameList.get(i).getUUID().equals(uuid)){
                usernameList.remove(i);
            }else{
                usernameList.get(i).setPassword("");
            }
        }
        return usernameList;
    }

    /**
     * New log entry into the database
     * @param message
     * @param title
     * @param uuid
     * @param recipient_uuid
     * @return
     */
    @Override
    public String updateLog(String message, String title, String uuid, String recipient_uuid) {
        logPost post = new logPost();
        post.setRecipient_UUID(recipient_uuid);
        post.setUser_UUID(uuid);
        post.set_date(LocalDate.now());
        post.setDescription(message);
        post.setTitle(title);
        logRepository.save(post);
        return post.toString();
    }


    /**
     * Adds a chart to a new log entry
     * @param uuid uuid for the user saving the chart
     * @param type what kind of chart
     * @param data the data for the chart
     * @return
     */
    @Override
    public String updateLogChart(String uuid, String type, String data) {
        logPost post = new logPost();
        post.set_date(LocalDate.now());
        post.setTitle("Images stored per day as of " + LocalDate.now());
        post.setUser_UUID(uuid);
        post.setRecipient_UUID(uuid);
        post.setData(data);
        post.setType(type);
        logRepository.save(post);
        return "success";
    }


    /**
     * Gets the encrypted password
     * @return
     */
    @Bean
    private PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
