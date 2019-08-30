package com.woowacourse.dsgram;

import com.woowacourse.dsgram.domain.*;
import com.woowacourse.dsgram.domain.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DsGramApplication {
    public static void main(String[] args) {
        SpringApplication.run(DsGramApplication.class, args);
    }

}
