package com.example.coindesk.demo.service;

import com.example.coindesk.demo.bean.Coin;
import com.example.coindesk.demo.bean.User;
import com.example.coindesk.demo.dao.CoinRepository;
import com.example.coindesk.demo.dao.UserRepository;

import java.sql.Timestamp;
import java.util.List;

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

@Service("coinService")
public class CoinService {
    @Autowired
    private CoinRepository coinRepository;

    public Coin addTestCoin() {
        Coin coin = null;
        coin = new Coin();
        coin.setPcode("test_code");
        coin.setPdescription("test_description");
        String str = new String("1111.123456");
        str = str.replace(",", "");
        coin.setPrate(Double.parseDouble(str));
        coin.setPrate_float(Double.parseDouble("2222.12345"));
        String i18n = "";
        if ("&#36;".equals("&#36;"))
            i18n = "美元";

        coin.setPsymbol(i18n);
        return coinRepository.save(coin);
    }

    public List<Coin> getAllCoin() {
        return coinRepository.findAll();
    }

    public Coin delCoinByName(String p_code) {

        Coin coin = coinRepository.findByPcode(p_code);
        if(coin != null)
        coinRepository.delete(coin);
        
        return null;

    }

    public Coin getCoinByName(String p_code) {
        return coinRepository.findByPcode(p_code);
    }

    public Coin save(Coin u) {
        return coinRepository.save(u);
    }

    public Coin update(JSONObject data_obj) {
        // Get the required data using its key
        JSONObject cointype = (JSONObject) data_obj.get("bpi");

        // System.out.println(cointype.get("USD"));
        String[] getCoinlist = new String[] { "USD", "GBP", "EUR" };
        for (String coinName : getCoinlist) {
            // System.out.println(test);

            JSONObject tmp = (JSONObject) cointype.get(coinName);
            // System.out.println(tmp);
            String code = (String) tmp.getAsString("code");
            String symbol = (String) tmp.getAsString("symbol");
            String rate = (String) tmp.getAsString("rate");
            String rate_float = (String) tmp.getAsString("rate_float");
            String description = (String) tmp.getAsString("description");

            Coin coin = coinRepository.findByPcode(coinName);
            // coin = new Coin();
            coin.setPcode(code);
            coin.setPdescription(description);
            String str = new String(rate);
            str = str.replace(",", "");
            coin.setPrate(Double.parseDouble(str));
            coin.setPrate_float(Double.parseDouble(rate_float));
            String i18n = "";
            if ("&#36;".equals(symbol))
                i18n = "美元";
            else if ("&pound;".equals(symbol))
                i18n = "英鎊";
            else if ("&euro;".equals(symbol))
                i18n = "歐元";
            coin.setPsymbol(i18n);
            coinRepository.save(coin);

            // System.out.println(code);
            // System.out.println(symbol);
            // System.out.println(rate);
            // System.out.println(rate_float);
            // System.out.println(description);
        }
        return null;
    }

}
