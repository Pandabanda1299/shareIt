package user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.ShareItServer;
import ru.practicum.shareit.user.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = ShareItServer.class)
class UserDtoJsonTest {

    @Autowired
    private JacksonTester<UserDto> json;


    @Test
    void testSerialize() throws Exception {
        UserDto userDto = new UserDto(1L, "user@example.com", "John Doe");

        JsonContent<UserDto> result = json.write(userDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("user@example.com");
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("John Doe");
    }

    @Test
    void testDeserialize() throws Exception {
        String jsonContent = "{\"id\":1,\"email\":\"user@example.com\",\"name\":\"John Doe\"}";

        UserDto result = json.parseObject(jsonContent);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("user@example.com");
        assertThat(result.getName()).isEqualTo("John Doe");
    }
}