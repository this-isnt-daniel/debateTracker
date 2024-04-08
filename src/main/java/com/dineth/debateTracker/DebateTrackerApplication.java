package com.dineth.debateTracker;

import com.dineth.debateTracker.utils.ParseTabbycatXML;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DebateTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DebateTrackerApplication.class, args);
		for (int i = 0; i < 10; i++) {
			System.out.println("\n");
		}

		System.out.println("Debate Tracker Application Started");
		ParseTabbycatXML parser = new ParseTabbycatXML("src/main/resources/static/Wickys_2024.xml");
		parser.parseXML();
		parser.getTeamsAndSpeakers(parser.document);
	}

}
