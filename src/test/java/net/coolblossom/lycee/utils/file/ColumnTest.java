package net.coolblossom.lycee.utils.file;

import net.coolblossom.lycee.utils.file.entity.Column;
import net.coolblossom.lycee.utils.file.entity.DataFileRule;
import net.coolblossom.lycee.utils.file.entity.rules.IntegerRule;
import net.coolblossom.lycee.utils.file.exceptions.InvalidRecordException;
import net.coolblossom.lycee.utils.file.exceptions.RecordMappingFailure;
import net.coolblossom.lycee.utils.file.mapper.DefaultEntityMapper;
import net.coolblossom.lycee.utils.file.mapper.FieldSet;
import net.coolblossom.lycee.utils.file.mapper.FieldSetImpl;
import net.coolblossom.lycee.utils.file.mapper.RecordMapper;
import net.coolblossom.lycee.utils.file.mapper.RecordMapperFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ColumnTest {

    private FieldSetImpl testFieldSet() {
        FieldSetImpl fieldSet = new FieldSetImpl();

        fieldSet.add("id", "1");
        fieldSet.add("name", "alice");
        fieldSet.add("rank", "s1");

        return fieldSet;
    }

    public static class ColumnTestDefault {
        @Column
        public String id;

        @Column
        public String name;

        @Column
        public String rank;
    }

    @Test
    @DisplayName("正常系 - デフォルト値")
    public void test_default_1() throws NoSuchMethodException {
        // Arrange
        RecordMapper<ColumnTestDefault> mapper
                = new RecordMapperFactory().createMapper(ColumnTestDefault.class);

        // Act
        ColumnTestDefault result = mapper.map(testFieldSet());

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id).isEqualTo("1");
        assertThat(result.name).isEqualTo("alice");
        assertThat(result.rank).isEqualTo("s1");
    }


    @Nested
    @DisplayName("Column.orderのテスト")
    class ColumnOrderTest {
        public static class ColumnOrderTestData1 {
            @Column(order = 1)
            public String id;

            @Column(order = 2)
            public String name;

            @Column(order = 3)
            public String rank;
        }

        @Test
        @DisplayName("正常系 - 1始まりの列挙")
        public void test_order_1() throws NoSuchMethodException {
            // Arrange
            RecordMapper<ColumnOrderTestData1> mapper
                    = new RecordMapperFactory().createMapper(ColumnOrderTestData1.class);

            // Act
            ColumnOrderTestData1 result = mapper.map(testFieldSet());

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.id).isEqualTo("1");
            assertThat(result.name).isEqualTo("alice");
            assertThat(result.rank).isEqualTo("s1");
        }

        public static class ColumnOrderTestData2 {
            @Column(order = 1)
            public String id;
            @Column(order = 3)
            public String name;
            @Column(order = 4)
            public String rank;
        }

        @Test
        @DisplayName("正常系 - 途中で1つ抜け")
        public void test_order_2() throws NoSuchMethodException {
            // Arrange
            RecordMapper<ColumnOrderTestData2> mapper
                    = new RecordMapperFactory().createMapper(ColumnOrderTestData2.class);

            // Act
            ColumnOrderTestData2 result = mapper.map(testFieldSet());

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.id).isEqualTo("1");
            assertThat(result.name).isEqualTo("s1");
            assertThat(result.rank).isNull();
        }

        public static class ColumnOrderTestData3 {
            @Column(order = 1)
            public String id;
            @Column(order = 2)
            public String name;
            @Column(order = 2)
            public String rank;
        }

        @Test
        @DisplayName("正常系 - 数値の重複")
        public void test_order_3() {
            // Arrange

            // Act & Assert
            assertThatThrownBy(() -> new RecordMapperFactory().createMapper(ColumnOrderTestData3.class))
                    .isExactlyInstanceOf(RecordMappingFailure.class);

        }

        public static class ColumnOrderTestData4 {
            @Column(order = 1)
            public String id;
            @Column(order = 2)
            public String name;
        }

        @Test
        @DisplayName("正常系 - ファイルの項目より少ない")
        public void test_order_4() throws NoSuchMethodException {
            // Arrange
            RecordMapper<ColumnOrderTestData4> mapper
                    = new RecordMapperFactory().createMapper(ColumnOrderTestData4.class);

            // Act
            ColumnOrderTestData4 result = mapper.map(testFieldSet());

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.id).isEqualTo("1");
            assertThat(result.name).isEqualTo("alice");
        }

        public static class ColumnOrderTestData5 {
            @Column(order = 1)
            public String id;
            @Column(order = 2)
            public String name;
            @Column(order = 3)
            public String rank;
            @Column(order = 4)
            public String comment;
        }

        @Test
        @DisplayName("正常系 - ファイルの項目より多い")
        public void test_order_5() throws NoSuchMethodException {
            // Arrange
            RecordMapper<ColumnOrderTestData5> mapper
                    = new RecordMapperFactory().createMapper(ColumnOrderTestData5.class);

            // Act
            ColumnOrderTestData5 result = mapper.map(testFieldSet());

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.id).isEqualTo("1");
            assertThat(result.name).isEqualTo("alice");
            assertThat(result.rank).isEqualTo("s1");
            assertThat(result.comment).isNull();
        }

        public static class ColumnOrderTestData6 {
            @Column(order = 2)
            public String id;
            @Column(order = 3)
            public String name;
            @Column(order = 4)
            public String rank;
        }

        @Test
        @DisplayName("正常系 - 2始まり")
        public void test_order_6() throws NoSuchMethodException {
            RecordMapper<ColumnOrderTestData6> mapper
                    = new RecordMapperFactory().createMapper(ColumnOrderTestData6.class);

            // Act
            ColumnOrderTestData6 result = mapper.map(testFieldSet());

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.id).isEqualTo("alice");
            assertThat(result.name).isEqualTo("s1");
        }
    }

    @Nested
    @DisplayName("Column.nameのテスト")
    class ColumnNameTest {
        public static class ColumnNameTestData1 {
            @Column(name = "id")
            public String memberId;
            @Column(name = "name")
            public String userName;
            @Column
            public String rank;
        }

        @Test
        @DisplayName("正常系 - ファイルに存在しない項目名")
        public void test_name_1() throws NoSuchMethodException {
            RecordMapper<ColumnNameTestData1> mapper
                    = new RecordMapperFactory().createMapper(ColumnNameTestData1.class);

            // Act
            ColumnNameTestData1 result = mapper.map(testFieldSet());

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.memberId).isEqualTo("1");
            assertThat(result.userName).isEqualTo("alice");
            assertThat(result.rank).isEqualTo("s1");
        }

    }

    @Nested
    @DisplayName("Column.ruleのテスト")
    class ColumnRuleTest {
        public static class TestRule implements DataFileRule<Integer> {

            @Override
            public Integer read(String column) throws InvalidRecordException {
                try {
                    return Integer.parseInt(column);
                } catch (NumberFormatException e) {
                    throw new InvalidRecordException(e);
                }
            }

            @Override
            public String write(Integer data) {
                return null;
            }
        }
        public static class ColumnRuleTestData1 {
            @Column(rule = TestRule.class)
            public String id;

            @Column
            public String name;

            @Column
            public String rank;
        }

        @Test
        @DisplayName("正常系 - 正しく変換できないルール")
        public void test_rule_1() throws NoSuchMethodException {
            // Arrange
            RecordMapper<ColumnRuleTestData1> mapper
                    = new RecordMapperFactory().createMapper(ColumnRuleTestData1.class);

            // Act & Assert
            assertThatThrownBy(() -> mapper.map(testFieldSet()))
                    .isExactlyInstanceOf(InvalidRecordException.class);
        }

    }

    @Nested
    @DisplayName("複合系")
    class ColumnComplexTest {

        public static class ColumnComplexTestData1 {
            @Column(order = 1, name = "systemId")
            public String memberId;

            @Column(order = 2, name = "member_name")
            public String userName;

            @Column(order = 10, name = "user_rank")
            public String gameRank;
        }

        @Test
        @DisplayName("正常系 - orderとnameの同時指定")
        public void test_complex_1() throws NoSuchMethodException {
            RecordMapper<ColumnComplexTestData1> mapper
                    = new RecordMapperFactory().createMapper(ColumnComplexTestData1.class);

            // Act
            ColumnComplexTestData1 result = mapper.map(testFieldSet());

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.memberId).isEqualTo("1");
            assertThat(result.userName).isEqualTo("alice");
            assertThat(result.gameRank).isNull();
        }
    }

}
