package com.chubaievskyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class, args);

        // TODO: 27.01.2024  Created by: @chubaievskyi - перехопити загальний ексепшн
        // TODO: 28.01.2024  Created by: @chubaievskyi - додати апдейтюзердто для оновлення користувачами пароля
        // TODO: 28.01.2024  Created by: @chubaievskyi - додати ендпойнт для оновлення пароля (власного)
        // TODO: 21.02.2024  Created by: @chubaievskyi - додати поле того хто оновив
        // TODO: 26.02.2024  Created by: @chubaievskyi - інтернаціоналізація, локалізація
        // TODO: 02.03.2024  Created by: @chubaievskyi - якщо введено не правильний статус?
        // TODO: 02.03.2024  Created by: @chubaievskyi - статус POSTPONE не працює
        // TODO: 02.03.2024  Created by: @chubaievskyi - зробити event DTO
    }
}