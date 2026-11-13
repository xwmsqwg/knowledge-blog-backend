package com.xrf.iotbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
        "spring.datasource.url=jdbc:mysql://localhost:3306/knowledge_blog",
        "spring.datasource.username=test",
        "spring.datasource.password=test"
})
class IotBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
