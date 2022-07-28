package com.example.coindesk.demo.controller;

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

@RestController
@RequestMapping("/")
public class ApiController {
	@Autowired
	MemberAccount memberAccount;
	@Autowired
	private UserService userService;
	@Autowired
	private CoinService coinService;

	@GetMapping("/getCoindeskApi/")
	public String getCoindeskApi() throws ParseException, IOException {
		String result="";
		URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();

		// Getting the response code
		int responsecode = conn.getResponseCode();
		System.out.println(responsecode);
		if (responsecode != 200) {
			throw new RuntimeException("HttpResponseCode: " + responsecode);
		} else {

			String inline = "";
			Scanner scanner = new Scanner(url.openStream());

			// Write all the JSON data into a string using a scanner
			while (scanner.hasNext()) {
				inline += scanner.nextLine();
			}

			// Close the scanner
			scanner.close();

			// Using the JSON simple library parse the string into a json object
			JSONParser parse = new JSONParser();
			JSONObject data_obj = (JSONObject) parse.parse(inline);
			result = data_obj.toString();
		
		}
		return result;
	}

	@GetMapping("/updataCoin/")
	public String updataCoin() throws ParseException, IOException {
		
		URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();

		// Getting the response code
		int responsecode = conn.getResponseCode();
		System.out.println(responsecode);
		if (responsecode != 200) {
			throw new RuntimeException("HttpResponseCode: " + responsecode);
		} else {

			String inline = "";
			Scanner scanner = new Scanner(url.openStream());

			// Write all the JSON data into a string using a scanner
			while (scanner.hasNext()) {
				inline += scanner.nextLine();
			}

			// Close the scanner
			scanner.close();

			// Using the JSON simple library parse the string into a json object
			JSONParser parse = new JSONParser();
			JSONObject data_obj = (JSONObject) parse.parse(inline);
			coinService.update(data_obj);
			userService.update(data_obj);
		}
		return "update ok";
	}

	@GetMapping("/addTestCoin/")
	public Coin addTestCoin() {
		return coinService.addTestCoin();
	}

	@GetMapping("/getAllCoin/")
	public List<Coin> getAllCoin() {
		return coinService.getAllCoin();
	}

	@GetMapping("/getAllCoinDesk/")
	public List<User> getAllCoinDesk() {
		return userService.getAllCoinDesk();
	}
	
	@GetMapping("/coinByName/{coinbyname}")
	public Coin getCoinByName(@PathVariable("coinbyname") String p_code) {
		return coinService.getCoinByName(p_code);
	}


	@GetMapping("/delcoinByName/{coinbyname}")
	public Coin delCoinByName(@PathVariable("coinbyname") String p_code) {
		return coinService.delCoinByName(p_code);
	}

	@RequestMapping("/initdata")
	public String initdata() throws IOException, ParseException {

		URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();

		// Getting the response code
		int responsecode = conn.getResponseCode();
		System.out.println(responsecode);
		if (responsecode != 200) {
			throw new RuntimeException("HttpResponseCode: " + responsecode);
		} else {

			String inline = "";
			Scanner scanner = new Scanner(url.openStream());

			// Write all the JSON data into a string using a scanner
			while (scanner.hasNext()) {
				inline += scanner.nextLine();
			}

			// Close the scanner
			scanner.close();

			// Using the JSON simple library parse the string into a json object
			JSONParser parse = new JSONParser();
			JSONObject data_obj = (JSONObject) parse.parse(inline);

			// Get the required object from the above created object
			JSONObject obj = (JSONObject) data_obj.get("time");

			String obj2 = (String) data_obj.getAsString("chartName");
			String obj3 = (String) data_obj.getAsString("disclaimer");
			// // JSONObject obj3 = (JSONObject) data_obj.get("chartName");
			// System.out.println((String)obj2.toString());
			// System.out.println(obj3);

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
			user = new User();
			user.setName((String) obj2);
			user.setDisclaimer((String) obj3);
			user.setUpdated(ts);
			user.setUpdatedISO(new Timestamp(ts2));
			user.setUpdateduk(ts3);
			userService.save(user);

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

				Coin coin = null;
				coin = new Coin();
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
				coinService.save(coin);

				// System.out.println(code);
				// System.out.println(symbol);
				// System.out.println(rate);
				// System.out.println(rate_float);
				// System.out.println(description);
			}

			// Connection c = null;

			// Statement stmt = null;

			// try {

			// Class.forName("org.sqlite.JDBC");

			// c = DriverManager.getConnection("jdbc:sqlite:test.db");

			// System.out.println("Database Opened...\n");

			// stmt = c.createStatement();

			// String sql = "CREATE TABLE coin " +

			// "(p_id INTEGER PRIMARY KEY AUTOINCREMENT," +
			// " p_code TEXT NOT NULL, " +
			// " p_name TEXT NOT NULL, " +
			// " p_rate REAL NOT NULL, " +
			// " p_description TEXT NOT NULL, " +
			// " p_rate_float REAL NOT NULL)" ;

			// stmt.executeUpdate(sql);

			// stmt.close();

			// c.close();

			// }

			// catch (Exception e) {

			// System.err.println(e.getClass().getName() + ": " + e.getMessage());

			// System.exit(0);

			// }

			// System.out.println("Table Product Created Successfully!!!");

			// }

			// System.out.println(parser.parseDateTime(jtdate).withZone());
			// org.joda.time.format.DateTimeFormatter parser =
			// ISODateTimeFormat.dateTimeNoMillis();
			// formatDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
			// localDateTime = LocalDateTime.from(formatDateTime.parse((String)
			// obj.get("updatedISO")));
			// ts = Timestamp.valueOf(localDateTime);
			// System.out.println(ts.getTime());
			// SimpleDateFormat ISO8601DATEFORMAT = new
			// SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.GERMANY);
			// String jtdate = (String) obj.get("updatedISO");

			// org.joda.time.format.DateTimeFormatter parser =
			// ISODateTimeFormat.dateTimeParser();
			// // System.out.println(parser2.parseDateTime(jtdate));
			// DateTime dateTimeHere = parser.parseDateTime(jtdate);
			// DateTime dateTimeInParis =
			// dateTimeHere.withZone(DateTimeZone.forID("Africa/Abidjan"));
			// System.out.println(dateTimeInParis.toLocalDate().);
		}
		return "init successfully!";
		// MemberAccount memberAccount = new MemberAccount();
		// memberAccount.setAddress("台北市");
		// memberAccount.setCellphone("09123456789");
		// memberAccount.setEmail("test@gmail.com");
		// memberAccount.setId(1);
		// memberAccount.setPassword("123456789");
		// return memberAccount;
	}

	// @GetMapping("/init")
	// public String init() {
	// User user = null;
	// for (int i = 0; i < 10; i++) {
	// user = new User();
	// user.setName("test" + i);
	// userService.save(user);
	// }
	// return "初始化完成。";
	// }

	// @GetMapping("/userByName/{username}")
	// public User getUserByName(@PathVariable("username") String username) {
	// return userService.getByName(username);
	// }

	// @GetMapping("/userById/{userid}")
	// public User getUserById(@PathVariable("userid") Long userid) {
	// return userService.getUserByID(userid);
	// }

	// @GetMapping("/page")
	// public Page<User> getPage() {
	// return userService.findPage();
	// }

	// @GetMapping("/page/{maxID}")
	// public Page<User> getPageByMaxID(@PathVariable("maxID") Long maxID) {
	// return userService.find(maxID);
	// }

	// @RequestMapping("/update/{name}")
	// public User update( @PathVariable String name) {
	// return userService.update( name);
	// }

	// @RequestMapping("/update/{id}")
	// public Boolean updateById(@PathVariable Long id) {
	// return userService.updateById("newName", id);
	// }

	// @RequestMapping("/updatetime/{time}")
	// public Boolean updateTime(@PathVariable("username") String time) {
	// return userService.updateTime(time);
	// }

}