package net.coolblossom.lycee.utils.file;

import net.coolblossom.lycee.utils.file.manip.DataFileConfig;
import net.coolblossom.lycee.utils.file.entity.*;
import net.coolblossom.lycee.utils.file.manip.DataFileReader;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


public class DataFileReaderTest {

    public static class UserInfo {
        @Column(name = "user_id")
        public String userId;
        @Column(name = "user_name")
        public String userName;
        public int age;

        @Override
        public String toString() {
            return "UserInfo{" +
                    "userId='" + userId + '\'' +
                    ", userName='" + userName + '\'' +
                    ", age='" + age + '\'' +
                    '}';
        }
    }

    @Test
    public void test() throws Exception {
        DataFileConfig config = DataFileConfig.create();

        try (DataFileReader<UserInfo> reader = DataFileReader.create(config, "test.csv", UserInfo.class)) {
            reader.forEach(data -> {
                assertThat(data).isNotNull();
            });
        }
    }

    @Test
    public void test_iterate() throws Exception {
        DataFileConfig config = DataFileConfig.create()
                .fieldSeparator(',')
                .fieldWrap('\"', false);
        String filename = "src/test/resources/test.csv";
        try (DataFileReader<UserInfo> reader = DataFileReader.create(config, filename, UserInfo.class)) {
            for (UserInfo entity: reader) {
                System.out.println(entity);
            }
        }
    }

    @Test
    public void test_ready() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/test/resources/test.csv"))) {
            while(true) {
                boolean isReady = reader.ready();
                int value = reader.read();

                System.out.printf("%b - %c\n", isReady, (char)value);

                if(value == -1) {
                    break;
                }
            }

        }


    }

}
