import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DbUserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public UserDto create(UserDto dto) {
        User user = repository.save(UserMapper.mapDtoToUser(dto));
        return UserMapper.mapUserToDto(user);
    }

    @Override
    public UserDto update(UserDto dto, Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Обновляемый пользователь не найден"));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            if (repository.findByEmail(dto.getEmail()).isEmpty()) {
                user.setEmail(dto.getEmail());
            } else {
                log.error("Указанный email существует - {}", dto.getEmail());
                throw new ConflictException("Указанный email существует");
            }
        }

        repository.save(user);
        return UserMapper.mapUserToDto(user);
    }

    @Override
    public void delete(Long id) {
        User user = repository.getReferenceById(id);
        repository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<User> users = repository.findAll();
        return UserMapper.mapUserToDto(users);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        return UserMapper.mapUserToDto(user);
    }
}