package com.cornello.prototype.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.web.util.UriComponentsBuilder;

public class TestGround {
    public static void main(String[] args) {
        // create a list of integers
        List<Integer> number = Arrays.asList(2,3,4,5);
        // demonstration of map method
        List<Integer> square = number.stream().map(x -> x*x).collect(Collectors.toList());
        number.stream().forEach(y->System.out.println(y));
        System.out.println("square:"+square);
        int even = number.stream().filter(x->x%2==0).reduce(0,(x,y)->x+y);
        // int even = number.stream().filter(x->x%2==0).reduce(0,Integer::sum);
        System.out.println("even:"+even);
        //
        String lifelineName = "I/Lie Chu Rie/@";
        List<String> names = Arrays.asList(lifelineName.replace("@","").split("/"));
        System.out.println("Names: "+names);

        Person person1 =  new Person("Yeah");
        Person person2 =  new Person("Baijini");
        List<Person> persons = new ArrayList<>();
        List<Person> personsModified = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        for(Person p : persons) {
            Person p_ = new Person();
            if("Yeah".equalsIgnoreCase(p.getName())) {
                p_.setName("XX");
            }else {
                p_ = p;
            }
            personsModified.add(p_);
        }
        String test = ",yeye";
        System.out.println("Persons after for each: "+persons);
        System.out.println("Double parse: "+Double.valueOf("1234.34"));
        System.out.println("personsModified after for each: "+personsModified);
        System.out.println("subString "+test.substring(1,test.length()));
        System.out.println("replace email "+"()promega@cpi.com".replaceAll("[^a-zA-Z0-9Ññ/@.#_+, -]", ""));
        System.out.println("".isBlank());
        System.out.println("".trim());
        System.out.println(" ".isEmpty());
        LocalDate dateNow = LocalDate.now();
        LocalDate dateTomm= dateNow.plusMonths(1);
        System.out.println("dateNow"+dateNow.toString());
        System.out.println("dateTomm"+dateTomm.toString());
        
        System.out.println("dateNow.isAfter(dateTomm):"+dateNow.isAfter(dateTomm));
        System.out.println("dateTomm.isAfter(dateNow):"+dateTomm.isAfter(dateNow));

        Calendar c = Calendar.getInstance();
        // set Month
        // MONTH starts with 0 i.e. ( 0 - Jan)
        c.set(Calendar.MONTH, 11);
        // set Date
        c.set(Calendar.DATE, 05);
        // set Year
        c.set(Calendar.YEAR, 1996);
        // creating a date object with specified time.
        Date dateOne = c.getTime();
        // creating a date of object
        // storing the current date
        Date currentDate = new Date();
        System.out.print("Is currentDate after date one : ");
        // if currentDate is after dateOne
        System.out.println(currentDate.after(dateOne));
        
        LocalDate localDateNow = LocalDate.parse("March 1, 2022",DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        System.out.println("localDateNow:"+localDateNow);
        Random random = new Random();
        System.out.println("chances:"+random.nextBoolean());

        String fullMessage = "";
        for(String name : names) {
            fullMessage = fullMessage + ", "+name;
        }
        System.out.println(fullMessage.substring(2,fullMessage.length()));
        System.out.println( maskMidCharsMobile("+639217045345") );
        boolean hasusername = containsSequences("brian_espina24@yahoo.com", "Brian@24");
        System.out.println("hasusername:"+hasusername);

        Map<String, String> pathVariables = new HashMap<String, String>();
        pathVariables.put("oktaUserId","123456");
        UriComponentsBuilder builder =  UriComponentsBuilder.fromUriString("https://aiacvad.cloud.com/Citrix/{oktaUserId}/factors");
        System.out.println("final url"+ builder.buildAndExpand(pathVariables).toUri());
        System.out.println("final url"+ builder.buildAndExpand(pathVariables).toUriString());
    }

    public static Boolean containsSequences(String uname, String pwd){
        Boolean contains=false;
        String regexSplitter = "[@&?$+-_]+";
        pwd = pwd.toLowerCase();
        uname = uname.toLowerCase();
        outerloop : for (String seq: uname.split(regexSplitter)) { 
            System.out.println("seq:"+seq);
            for (String seq2: pwd.split(regexSplitter)) {
                if(seq.contains(seq2) ) {
                    System.out.println("seq2:"+seq2);
                    contains=true;
                    break outerloop;
                }
            }
        }
        return contains;
      }

    static class Person {
        private String name;
        Person() {
        }
        Person(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
        public void setName(String name){
            this.name = name;
        }
        @Override
        public String toString() {
            return "Person [name=" + name + "]";
        }
    }

    public static <E> E nvl(E expr1, E expr2) {
		return null == expr1 ? expr2 : expr1;
	}

	public static String maskMidCharsMobile(String mobile) {
		if(null == mobile || mobile.isEmpty()) {
			return "";
		}
		//+639217045345 -> +639XXXXX5345
        return mobile.replaceAll("\\b(\\d{2})\\d+(\\d{4})", "$1"+mobile.substring(3,mobile.length()-4).replaceAll(".", "X")+"$2");
	}

	public static String maskEmail(String email) {
		if(null == email || email.isEmpty()) {
			return "";
		}
		String[] strings = email.split("@");
		int emailLen = strings[0].length();
		if(emailLen > 4) {
			//promega@cpi.com.ph -> pr***ga@cpi.com.ph
			return email.replaceAll("(\\G(?!^)|^[^@]{2})[^@](?=[^@]{2,}@)", "$1*");
		} else if(emailLen >= 3) { 
			//prom@cpi.com.ph -> p**m@cpi.com.ph
			return email.replaceAll("(\\G(?!^)|^[^@]{1})[^@](?=[^@]{1,}@)", "$1*");
		} else {
			return email.replaceAll("(\\G(?!^)|^[^@]{1})[^@](?=[^@]{0,}@)", "$1*");
		}
	}
}
