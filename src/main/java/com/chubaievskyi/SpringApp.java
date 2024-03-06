package com.chubaievskyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class, args);

        // TODO: 27.01.2024  Created by: @chubaievskyi - перехопити загальний ексепшн
        // TODO: 26.02.2024  Created by: @chubaievskyi - інтернаціоналізація, локалізація
        // TODO: 06.03.2024  Created by: @chubaievskyi - користувача не знайдено з ІД!!! (e.get message в глобалексепшнхендлері)
        // TODO: 06.03.2024  Created by: @chubaievskyi - право перевідкривати таску тільки у адміна
        // TODO: 06.03.2024  Created by: @chubaievskyi - перевірити роботу всіх ендпоінтів та всіх змін статусів
    }
}