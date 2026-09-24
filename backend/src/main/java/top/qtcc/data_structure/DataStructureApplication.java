package top.qtcc.data_structure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("top.qtcc.data_structure.mapper")
@SpringBootApplication
public class DataStructureApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataStructureApplication.class, args);
    }

}
