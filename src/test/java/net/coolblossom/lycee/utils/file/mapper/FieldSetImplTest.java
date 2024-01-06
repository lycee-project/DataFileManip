package net.coolblossom.lycee.utils.file.mapper;

import net.coolblossom.lycee.utils.file.exceptions.InvalidNameException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FieldSetImplTest {
    @Test
    public void test_add() {
        // Arrange
        FieldSetImpl target = new FieldSetImpl();

        // Act
        target.add("data1", "10");
        target.add("data2", "20");
        target.add("data3", "30");
        target.add("40");

        // Assert
        assertThat(target.getStringAt(0)).isEqualTo("10");
        assertThat(target.getStringAt(1)).isEqualTo("20");
        assertThat(target.getStringAt(2)).isEqualTo("30");
        assertThat(target.getStringAt(3)).isEqualTo("40");
    }

    @Test
    public void test_has() {
        // Arrange
        FieldSetImpl target = new FieldSetImpl();
        target.add("data1", "10");
        target.add("data2", "20");
        target.add("data3", "30");

        // Act & Assert
        assertThat(target.has("data1")).isTrue();
        assertThat(target.has("data4")).isFalse();
    }

    @Test
    public void test_getString() {
        // Arrange
        FieldSetImpl target = new FieldSetImpl();
        target.add("data1", "10");
        target.add("data2", "20");
        target.add("data3", "30");

        // Act
        String actual = target.getString("data2");

        // Assert
        assertThat(actual).isEqualTo("20");
    }

    @Test
    public void test_getInt() {
        // Arrange
        FieldSetImpl target = new FieldSetImpl();
        target.add("data1", "10");
        target.add("data2", "20");
        target.add("data3", "30");

        // Act
        Integer actual = target.getInt("data2");

        // Assert
        assertThat(actual).isEqualTo(20);
    }

    @Test
    public void test_getStringAt() {
        // Arrange
        FieldSetImpl target = new FieldSetImpl();
        target.add("data1", "10");
        target.add("data2", "20");
        target.add("data3", "30");

        // Act
        String actual = target.getStringAt(2);

        // Assert
        assertThat(actual).isEqualTo("30");
    }

    @Test
    public void test_getIntAt() {
        // Arrange
        FieldSetImpl target = new FieldSetImpl();
        target.add("data1", "10");
        target.add("data2", "20");
        target.add("data3", "30");

        // Act
        Integer actual = target.getIntAt(0);

        // Assert
        assertThat(actual).isEqualTo(10);
    }

    @Test
    public void test_getStringInvalidName() {
        // Arrange
        FieldSetImpl target = new FieldSetImpl();
        target.add("data1", "10");
        target.add("data2", "20");
        target.add("data3", "30");

        // Act & Arrange
        assertThatThrownBy(() -> target.getString("data4"))
                .isExactlyInstanceOf(InvalidNameException.class)
                .hasMessage("data4");
        }


}
