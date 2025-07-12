package org.example.order;

import org.example.order.feign.WeatherFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherTest {
    @Autowired
    WeatherFeignClient weatherFeignClient;
    @Test
    void test01(){
        String weather = weatherFeignClient.getWeather("auth",
                "token",
                "cityId");
        System.out.println(weather);
    }
}
