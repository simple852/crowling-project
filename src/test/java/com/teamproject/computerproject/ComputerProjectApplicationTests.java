package com.teamproject.computerproject;

import com.teamproject.computerproject.dto.UserDto;
import com.teamproject.computerproject.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ComputerProjectApplicationTests {

	@Test
	void contextLoads() throws Exception {
		NotificationService notificationService = new NotificationService();

		notificationService.send_notification("제목","내용","https://prod.danawa.com/info/?pcode=28798964&cate=113973&adinflow=Y","https://img.danawa.com/prod_img/500000/964/798/img/28798964_1.jpg?shrink=330:*&_v=20231031110649");
	}

}
