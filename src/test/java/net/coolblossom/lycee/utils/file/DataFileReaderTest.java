package net.coolblossom.lycee.utils.file;

import lombok.Data;
import net.coolblossom.lycee.utils.file.entity.Column;
import net.coolblossom.lycee.utils.file.entity.DataFileRule;
import net.coolblossom.lycee.utils.file.exceptions.InvalidRecordException;
import net.coolblossom.lycee.utils.file.exceptions.RecordMappingFailure;
import net.coolblossom.lycee.utils.file.manip.DataFileConfig;
import net.coolblossom.lycee.utils.file.manip.DataFileReader;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


public class DataFileReaderTest {

    public enum Gender {
        Male,
        Female
    }

    public enum UserRank {
        Bronze(1),
        Silver(2),
        Gold(3),
        Platinum(4)
        ;

        private final int rankCode;

        private UserRank(int rankCode) {
            this.rankCode = rankCode;
        }
        public static class UserRankRule implements DataFileRule<UserRank> {

            @Override
            public UserRank read(String column) {
                return Arrays.stream(UserRank.values())
                        .filter(e -> String.valueOf(e.rankCode).equals(column))
                        .findFirst()
                        .orElseThrow(InvalidRecordException::new);
            }

            @Override
            public String write(UserRank data) {
                return String.valueOf(data.rankCode);
            }
        }
    }

    public static class UserPoint {
        private final long point;

        public UserPoint(long pt) {
            this.point = pt;
        }

        public long toLong() {
            return point;
        }

        @Override
        public String toString() {
            return String.valueOf(point);
        }

        public static class UserPointRule implements DataFileRule<UserPoint> {

            @Override
            public UserPoint read(String column) throws InvalidRecordException {
                try {
                    return new UserPoint(Long.parseLong(column));
                } catch (NumberFormatException nfe) {
                    throw new InvalidRecordException(nfe);
                }
            }

            @Override
            public String write(UserPoint data) {
                return data.toString();
            }
        }
    }

    @Data
    public static class TestRecord {
        // プリミティブ型
        @Column(order = 1)
        private int recordNum;

        // ボクシング
        @Column(order = 2)
        private Integer userId;

        // 標準オブジェクト型
        @Column(order = 3)
        private String userName;

        // enum
        @Column(order = 4)
        private Gender gender;

        // ルールありenum
        @Column(order = 5, rule = UserRank.UserRankRule.class)
        private UserRank rank;

        // ユーザ定義
        @Column(order = 6, rule = UserPoint.UserPointRule.class)
        private UserPoint point;

    }


    @Test
    public void test_csv () throws Exception {
        DataFileConfig csvConfig = DataFileConfig.create()
                .lineSeparator("\r\n")
                .hasHeader(true)
                .fieldSeparator(',')
                .fieldWrap('\"', false)
                .invalidRecord(DataFileConfig.Record.Skip)
                ;

        String filePath = "src/test/resources/users.csv";

        try (DataFileReader<TestRecord> reader = DataFileReader.create(csvConfig, filePath, TestRecord.class)) {
            reader.forEach(rec -> {
                System.out.println(rec);
            });
        }

    }




}
