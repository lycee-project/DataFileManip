package net.coolblossom.lycee.utils.file;

import net.coolblossom.lycee.utils.file.manip.DataFileConfig;
import net.coolblossom.lycee.utils.file.manip.DataFileReader;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class DataFileTest {

    static class UserInfo {
        String userId;
        String userName;
        Integer age;

        List<String> profile;
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
    public void test_read() throws Exception {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/test/resources/test.csv")) ) {
            String header = reader.readLine();
            String[] columns = Arrays.stream(header.split(","))
                    .map(String::trim)
                    .toArray(String[]::new);

           // UserInfoMapper mapper = new UserInfoMapper(columns);
            reader.lines().forEach(System.out::println);
        }
    }

    @Test
    public void test_class() throws IllegalAccessException {
        Field[] fields = UserInfo.class.getDeclaredFields();
        String[] data = {"1234", "alice", "20"};
        String[] columns = {"userId", "userName", "age"};

        UserInfo target = new UserInfo();

        for(int i=0; i<columns.length; i++) {
            String col = columns[i];
            String value = data[i];

            // カラムとフィールドをマッピングするのはMapper
            Field field = Arrays.stream(fields)
                    .filter(f -> f.getName().equals(col))
                    .findFirst().get();

            // ここの切り替え処理はBinderにする
            if (field.getType().equals(Integer.class)) {
                field.set(target, Integer.valueOf(value));
            } else {
                field.set(target, value);
            }
        }

        System.out.printf("%s - %s(%s)\n", target.userId, target.userName, target.age);

    }

    @Test
    public void test_list() {
        for(Field f: UserInfo.class.getDeclaredFields()) {
            System.out.println("*** *** *** *** *** *** *** ***");
            Class<?> type = f.getType();
            System.out.println(type);

            if (type.equals(List.class)) {
                // Listの型取得するときの書き方
                ParameterizedType listType = (ParameterizedType) f.getGenericType();
                for (Type paramType : listType.getActualTypeArguments()) {
                    System.out.println(paramType); // -> String
                }
            }
        }

    }
}
