package com.cornello.prototype;

import com.cornello.prototype.exception.ExceptionHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class PrototypeApplicationTests {

	@Test
	void contextLoads() {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		log.info("TEST generateRandomPassword: {}",exceptionHelper.generateRandomPassword(14));
	}

}
