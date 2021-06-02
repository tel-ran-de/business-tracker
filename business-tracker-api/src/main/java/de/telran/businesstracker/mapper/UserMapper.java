package de.telran.businesstracker.mapper;

import de.telran.businesstracker.data.User;
import de.telran.businesstracker.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .build();
    }
}
