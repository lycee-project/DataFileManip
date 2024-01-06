package net.coolblossom.lycee.utils.file.entity;

import lombok.Getter;
import lombok.Setter;
import net.coolblossom.lycee.utils.file.exceptions.InvalidRecordException;
import net.coolblossom.lycee.utils.file.exceptions.RecordMappingFailure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EntityDescTest {
    public enum Rank {
        Bronze,
        Silver,
        Gold
    }
    @Getter
    public static class Point {
        int value;
        public Point(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static class PointRule implements DataFileRule<Point> {

            @Override
            public Point read(String column) throws InvalidRecordException {
                if (column == null || column.isEmpty()) {
                    return null;
                }
                return new Point(Integer.parseInt(column));
            }

            @Override
            public String write(Point data) {
                return data.toString();
            }
        }
    }

    public static class EntityDescTestDataPublic {
        @Column
        public boolean rawBool;
        @Column
        public Boolean boxedBool;
        @Column
        public byte rawByte;
        @Column
        public Byte boxedByte;
        @Column
        public short rawShort;
        @Column
        public Short boxedShort;
        @Column
        public char rawChar;
        @Column
        public Character boxedChar;
        @Column
        public int rawInt;
        @Column
        public Integer boxedInt;
        @Column
        public long rawLong;
        @Column
        public Long boxedLong;
        @Column
        public float rawFloat;
        @Column
        public Float boxedFloat;
        @Column
        public double rawDouble;
        @Column
        public Double boxedDouble;
        @Column
        public String text;
        @Column
        public Rank rank;
        @Column(rule = Point.PointRule.class)
        public Point point;

        public void setText(String text) {
            // 呼ばれないはず
            throw new RuntimeException();
        }
    }

    @Setter
    public static class EntityDescTestDataPrivate {
        @Column
        private boolean rawBool;
        @Column
        private Boolean boxedBool;
        @Column
        private byte rawByte;
        @Column
        private Byte boxedByte;
        @Column
        private short rawShort;
        @Column
        private Short boxedShort;
        @Column
        private char rawChar;
        @Column
        private Character boxedChar;
        @Column
        private int rawInt;
        @Column
        private Integer boxedInt;
        @Column
        private long rawLong;
        @Column
        private Long boxedLong;
        @Column
        private float rawFloat;
        @Column
        private Float boxedFloat;
        @Column
        private double rawDouble;
        @Column
        private Double boxedDouble;
        @Column
        private String text;
        @Column
        private Rank rank;
        @Column(rule = Point.PointRule.class)
        private Point point;
    }

    @Test
    @DisplayName("publicフィールドに正常な値を設定")
    public void testPublic() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        EntityDesc<EntityDescTestDataPublic> target = new EntityDesc<>(EntityDescTestDataPublic.class);
        EntityDescTestDataPublic entity = new EntityDescTestDataPublic();

        // Act
        target.bindValue(entity, "rawBool", "true");
        target.bindValue(entity, "boxedBool", "false");
        target.bindValue(entity, "rawByte", "1");
        target.bindValue(entity, "boxedByte", "10");
        target.bindValue(entity, "rawShort", "1");
        target.bindValue(entity, "boxedShort", "10");
        target.bindValue(entity, "rawChar", "a");
        target.bindValue(entity, "boxedChar", "b");
        target.bindValue(entity, "rawInt", "1");
        target.bindValue(entity, "boxedInt", "10");
        target.bindValue(entity, "rawLong", "1");
        target.bindValue(entity, "boxedLong", "10");

        target.bindValue(entity, "rawFloat", "12.34");
        target.bindValue(entity, "boxedFloat", "5.678");
        target.bindValue(entity, "rawDouble", "12.34");
        target.bindValue(entity, "boxedDouble", "5.678");

        target.bindValue(entity, "text", "comment");

        target.bindValue(entity, "rank", "Silver");
        target.bindValue(entity, "point", "100");

        // Assert
        assertThat(entity.rawBool).isTrue();
        assertThat(entity.boxedBool).isFalse();
        assertThat(entity.rawByte).isEqualTo((byte) 1);
        assertThat(entity.boxedByte).isEqualTo((byte) 10);
        assertThat(entity.rawShort).isEqualTo((short) 1);
        assertThat(entity.boxedShort).isEqualTo((short) 10);
        assertThat(entity.rawChar).isEqualTo('a');
        assertThat(entity.boxedChar).isEqualTo('b');
        assertThat(entity.rawInt).isEqualTo(1);
        assertThat(entity.boxedInt).isEqualTo(10);
        assertThat(entity.rawLong).isEqualTo(1);
        assertThat(entity.boxedLong).isEqualTo(10);
        assertThat(entity.rawFloat).isEqualTo(12.34f);
        assertThat(entity.boxedFloat).isEqualTo(5.678f);
        assertThat(entity.rawDouble).isEqualTo(12.34);
        assertThat(entity.boxedDouble).isEqualTo(5.678);

        assertThat(entity.text).isEqualTo("comment");

        assertThat(entity.rank).isEqualTo(Rank.Silver);
        assertThat(entity.point.getValue()).isEqualTo(100);
    }

    @Test
    @DisplayName("publicフィールドに空文字を設定")
    public void testPublicBlankValue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        EntityDesc<EntityDescTestDataPublic> target = new EntityDesc<>(EntityDescTestDataPublic.class);
        EntityDescTestDataPublic entity = new EntityDescTestDataPublic();

        // Act
        target.bindValue(entity, "rawBool", "");
        target.bindValue(entity, "boxedBool", "");
        target.bindValue(entity, "rawByte", "");
        target.bindValue(entity, "boxedByte", "");
        target.bindValue(entity, "rawShort", "");
        target.bindValue(entity, "boxedShort", "");
        target.bindValue(entity, "rawChar", "");
        target.bindValue(entity, "boxedChar", "");
        target.bindValue(entity, "rawInt", "");
        target.bindValue(entity, "boxedInt", "");
        target.bindValue(entity, "rawLong", "");
        target.bindValue(entity, "boxedLong", "");

        target.bindValue(entity, "rawFloat", "");
        target.bindValue(entity, "boxedFloat", "");
        target.bindValue(entity, "rawDouble", "");
        target.bindValue(entity, "boxedDouble", "");

        target.bindValue(entity, "text", "");

        target.bindValue(entity, "rank", "");
        target.bindValue(entity, "point", "");

        // Assert
        assertThat(entity.rawBool).isTrue();
        assertThat(entity.boxedBool).isNull();
        assertThat(entity.rawByte).isEqualTo((byte) 0);
        assertThat(entity.boxedByte).isNull();
        assertThat(entity.rawShort).isEqualTo((short) 0);
        assertThat(entity.boxedShort).isNull();
        assertThat(entity.rawChar).isEqualTo((char) 0);
        assertThat(entity.boxedChar).isNull();
        assertThat(entity.rawInt).isEqualTo(0);
        assertThat(entity.boxedInt).isNull();
        assertThat(entity.rawLong).isEqualTo(0);
        assertThat(entity.boxedLong).isNull();
        assertThat(entity.rawFloat).isEqualTo(0);
        assertThat(entity.boxedFloat).isNull();
        assertThat(entity.rawDouble).isEqualTo(0);
        assertThat(entity.boxedDouble).isNull();

        assertThat(entity.text).isEmpty();

        assertThat(entity.rank).isNull();
        assertThat(entity.point).isNull();
    }

    @Test
    @DisplayName("privateフィールドに正常な値を設定")
    public void testPrivate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        EntityDesc<EntityDescTestDataPrivate> target = new EntityDesc<>(EntityDescTestDataPrivate.class);
        EntityDescTestDataPrivate entity = new EntityDescTestDataPrivate();

        // Act
        target.bindValue(entity, "rawBool", "true");
        target.bindValue(entity, "boxedBool", "false");
        target.bindValue(entity, "rawByte", "1");
        target.bindValue(entity, "boxedByte", "10");
        target.bindValue(entity, "rawShort", "1");
        target.bindValue(entity, "boxedShort", "10");
        target.bindValue(entity, "rawChar", "a");
        target.bindValue(entity, "boxedChar", "b");
        target.bindValue(entity, "rawInt", "1");
        target.bindValue(entity, "boxedInt", "10");
        target.bindValue(entity, "rawLong", "1");
        target.bindValue(entity, "boxedLong", "10");

        target.bindValue(entity, "rawFloat", "12.34");
        target.bindValue(entity, "boxedFloat", "5.678");
        target.bindValue(entity, "rawDouble", "12.34");
        target.bindValue(entity, "boxedDouble", "5.678");

        target.bindValue(entity, "text", "comment");

        target.bindValue(entity, "rank", "Silver");
        target.bindValue(entity, "point", "100");

        // Assert
        assertThat(entity.rawBool).isTrue();
        assertThat(entity.boxedBool).isFalse();
        assertThat(entity.rawByte).isEqualTo((byte) 1);
        assertThat(entity.boxedByte).isEqualTo((byte) 10);
        assertThat(entity.rawShort).isEqualTo((short) 1);
        assertThat(entity.boxedShort).isEqualTo((short) 10);
        assertThat(entity.rawChar).isEqualTo('a');
        assertThat(entity.boxedChar).isEqualTo('b');
        assertThat(entity.rawInt).isEqualTo(1);
        assertThat(entity.boxedInt).isEqualTo(10);
        assertThat(entity.rawLong).isEqualTo(1);
        assertThat(entity.boxedLong).isEqualTo(10);
        assertThat(entity.rawFloat).isEqualTo(12.34f);
        assertThat(entity.boxedFloat).isEqualTo(5.678f);
        assertThat(entity.rawDouble).isEqualTo(12.34);
        assertThat(entity.boxedDouble).isEqualTo(5.678);

        assertThat(entity.text).isEqualTo("comment");

        assertThat(entity.rank).isEqualTo(Rank.Silver);
        assertThat(entity.point.getValue()).isEqualTo(100);
    }

    @Test
    @DisplayName("privateフィールドに空文字を設定")
    public void testPrivateBlankValue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        EntityDesc<EntityDescTestDataPrivate> target = new EntityDesc<>(EntityDescTestDataPrivate.class);
        EntityDescTestDataPrivate entity = new EntityDescTestDataPrivate();

        // Act
        target.bindValue(entity, "rawBool", "");
        target.bindValue(entity, "boxedBool", "");
        target.bindValue(entity, "rawByte", "");
        target.bindValue(entity, "boxedByte", "");
        target.bindValue(entity, "rawShort", "");
        target.bindValue(entity, "boxedShort", "");
        target.bindValue(entity, "rawChar", "");
        target.bindValue(entity, "boxedChar", "");
        target.bindValue(entity, "rawInt", "");
        target.bindValue(entity, "boxedInt", "");
        target.bindValue(entity, "rawLong", "");
        target.bindValue(entity, "boxedLong", "");

        target.bindValue(entity, "rawFloat", "");
        target.bindValue(entity, "boxedFloat", "");
        target.bindValue(entity, "rawDouble", "");
        target.bindValue(entity, "boxedDouble", "");

        target.bindValue(entity, "text", "");

        target.bindValue(entity, "rank", "");
        target.bindValue(entity, "point", "");

        // Assert
        assertThat(entity.rawBool).isTrue();
        assertThat(entity.boxedBool).isNull();
        assertThat(entity.rawByte).isEqualTo((byte) 0);
        assertThat(entity.boxedByte).isNull();
        assertThat(entity.rawShort).isEqualTo((short) 0);
        assertThat(entity.boxedShort).isNull();
        assertThat(entity.rawChar).isEqualTo((char) 0);
        assertThat(entity.boxedChar).isNull();
        assertThat(entity.rawInt).isEqualTo(0);
        assertThat(entity.boxedInt).isNull();
        assertThat(entity.rawLong).isEqualTo(0);
        assertThat(entity.boxedLong).isNull();
        assertThat(entity.rawFloat).isEqualTo(0);
        assertThat(entity.boxedFloat).isNull();
        assertThat(entity.rawDouble).isEqualTo(0);
        assertThat(entity.boxedDouble).isNull();

        assertThat(entity.text).isEmpty();

        assertThat(entity.rank).isNull();
        assertThat(entity.point).isNull();
    }

    @Test
    @DisplayName("publicフィールドに変換できない値を設定")
    public void testInvalidValue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        EntityDesc<EntityDescTestDataPrivate> target = new EntityDesc<>(EntityDescTestDataPrivate.class);
        EntityDescTestDataPrivate entity = new EntityDescTestDataPrivate();

        // Act & Assert
        assertThatThrownBy(() -> target.bindValue(entity, "rawByte", "Undefined"))
                .isExactlyInstanceOf(InvalidRecordException.class);
        assertThatThrownBy(() -> target.bindValue(entity, "rawShort", "Undefined"))
                .isExactlyInstanceOf(InvalidRecordException.class);
        assertThatThrownBy(() -> target.bindValue(entity, "rawInt", "Undefined"))
                .isExactlyInstanceOf(InvalidRecordException.class);
        assertThatThrownBy(() -> target.bindValue(entity, "rawLong", "Undefined"))
                .isExactlyInstanceOf(InvalidRecordException.class);
        assertThatThrownBy(() -> target.bindValue(entity, "rawFloat", "Undefined"))
                .isExactlyInstanceOf(InvalidRecordException.class);
        assertThatThrownBy(() -> target.bindValue(entity, "rawDouble", "Undefined"))
                .isExactlyInstanceOf(InvalidRecordException.class);

        assertThatThrownBy(() -> target.bindValue(entity, "rank", "Undefined"))
                .isExactlyInstanceOf(InvalidRecordException.class);
        assertThatThrownBy(() -> target.bindValue(entity, "point", "Undefined"))
                .isExactlyInstanceOf(InvalidRecordException.class);
    }

    public static class IllegalDataPublic {
        @Column
        public Point point;
    }
    @Test
    @DisplayName("ruleの指定なし")
    public void testNoRule() {
        // Arrange
        assertThatThrownBy(() -> new EntityDesc<>(IllegalDataPublic.class))
                .isExactlyInstanceOf(RecordMappingFailure.class)
                .hasMessage("対応する型ではありません[net.coolblossom.lycee.utils.file.entity.EntityDescTest.Point]");
    }

    public static class IllegalDataPrivate {
        @Column(rule = Point.PointRule.class)
        private Point point;

        public void setUserPoint(Point pt) {
            this.point = pt;
        }
    }
    @Test
    @DisplayName("セッターが存在しない")
    public void testNoSetter() {
        // Arrange
        assertThatThrownBy(() -> new EntityDesc<>(IllegalDataPrivate.class))
                .isExactlyInstanceOf(RecordMappingFailure.class)
                .hasMessage("対応するメソッドがありません[point]");
    }

    public static class IllegalDataProtectedSetter {
        @Column(rule = Point.PointRule.class)
        private Point point;

        protected void setPoint(Point pt) {
            this.point = pt;
        }
    }
    @Test
    @DisplayName("セッターがpublicではない")
    public void testSetterNoPublic() {
        // Arrange
        assertThatThrownBy(() -> new EntityDesc<>(IllegalDataProtectedSetter.class))
                .isExactlyInstanceOf(RecordMappingFailure.class)
                .hasMessage("対応するメソッドがありません[point]");
    }

}
