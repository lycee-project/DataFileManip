package net.coolblossom.lycee.utils.file.entity;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class BinderTest {

    public enum Authority {
        Member(1),
        Administrator(2);
        int code;

        Authority(int val) {
            code = val;
        }

        public static class AuthorityRule implements DataFileRule<Authority> {

            @Override
            public Authority read(String column) {
                return null;
            }

            @Override
            public String write(Authority data) {
                return null;
            }
        }
    }

    public static class Info {
        public String name;

        public int age;

        public Integer rank;

        public Authority authority;

        private String nickname;

        public void setNickname(String name) {
            this.nickname = "★" + name + "★";
        }

        @Override
        public String toString() {
            return "Info{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", rank=" + rank +
                    ", authority=" + authority +
                    ", nickname='" + nickname + '\'' +
                    '}';
        }
    }

    @Test
    public void test_field() throws Exception {
        Class<?> clazz = Info.class;
        Info target = new Info();

        Field nameField = clazz.getDeclaredField("name");
        nameField.set(target, "alice");

        Field ageField = clazz.getDeclaredField("age");
        ageField.set(target, 10);

        Field rankField = clazz.getDeclaredField("rank");
        rankField.set(target, 1);

        System.out.println(target);
    }


}
