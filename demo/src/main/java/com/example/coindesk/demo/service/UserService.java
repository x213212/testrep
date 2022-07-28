package com.example.coindesk.demo.service;

import com.example.coindesk.demo.bean.User;
import com.example.coindesk.demo.dao.UserRepository;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coindesk.demo.model.*;
import com.example.coindesk.demo.bean.Coin;
import com.example.coindesk.demo.bean.User;
import com.example.coindesk.demo.service.CoinService;
import com.example.coindesk.demo.service.UserService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

@Service("userService")
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByID(Long id) {
        return userRepository.findById(id).get();
    }

    public User getByName(String name) {
        return userRepository.findByName(name);
    }

    public Page<User> findPage() {
        Pageable pageable = PageRequest.of(0, 10);
        return userRepository.findAll(pageable);
    }

    public Page<User> find(Long maxId) {
        Pageable pageable = PageRequest.of(0, 10);
        return userRepository.findMore(maxId, pageable);
    }

    public User save(User u) {
        return userRepository.save(u);
    }


    public  List<User>  getAllCoinDesk() {
        return userRepository.findAll();
    }

    public User update(JSONObject data_obj) {
       
        // Get the required object from the above created object
        JSONObject obj = (JSONObject) data_obj.get("time");
		String obj2 = (String) data_obj.getAsString("chartName");
		String obj3 = (String) data_obj.getAsString("disclaimer");

        DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss z");
        LocalDateTime localDateTime = LocalDateTime.from(formatDateTime.parse((String) obj.get("updated")));
        Timestamp ts = Timestamp.valueOf(localDateTime);

        System.out.println(ts.getTime());

        String jtdate = (String) obj.get("updatedISO");
        DateTime date = ISODateTimeFormat.dateTimeParser().parseDateTime(jtdate).withZone(DateTimeZone.UTC);
        long ts2 = new Timestamp(date.getMillis()).getTime();
        // System.out.println(new Timestamp(date.getMillis()).getTime());

        formatDateTime = DateTimeFormatter.ofPattern("MMM d, yyyy 'at' ss:mm z");
        System.out.println((String) obj.get("updateduk"));
        LocalDate dt = LocalDate.parse((String) obj.get("updateduk"), formatDateTime);

        Timestamp ts3 = Timestamp.valueOf(dt.atStartOfDay());
        // Timestamp ts2 = Timestamp.valueOf(dt.atStartOfDay());
        System.out.println(ts.getTime());

        User user = null;
        // for (int i = 0; i < 10; i++) {
        user = userRepository.findByName("Bitcoin");
        user.setName((String) obj2);
        user.setDisclaimer((String) obj3);
        user.setUpdated(ts);
        user.setUpdatedISO(new Timestamp(ts2));
        user.setUpdateduk(ts3);
        // userService.save(user);
        return userRepository.save(user);
    }

    public Boolean updateById(String name, Long id) {
        return userRepository.updateById(name, id) == 1;
    }

    public Boolean updateTime(String time) {
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd
        // HH:mm:ss");
        // String formatDateTime = time.format(formatter);
        // System.out.println(formatDateTime);
        Timestamp timestamp = Timestamp.valueOf(time);
        System.out.println(timestamp);

        return true;
    }

}
