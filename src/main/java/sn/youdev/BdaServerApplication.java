package sn.youdev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sn.youdev.services.TestService;

@SpringBootApplication
public class BdaServerApplication implements ApplicationRunner {

    private final TestService testService;

    @Autowired
    public BdaServerApplication(TestService testService) {
        this.testService = testService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BdaServerApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
            testService.run();
    }
}
