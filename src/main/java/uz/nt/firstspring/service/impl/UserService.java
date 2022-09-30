package uz.nt.firstspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.nt.firstspring.dto.JWTResponseDTO;
import uz.nt.firstspring.dto.LoginDTO;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.dto.UserInfoDto;
import uz.nt.firstspring.entity.User;
import uz.nt.firstspring.entity.UserSession;
import uz.nt.firstspring.repository.RedisRepository;
import uz.nt.firstspring.repository.UserRepository;
import uz.nt.firstspring.security.JwtUtil;
import uz.nt.firstspring.service.mapper.UserMapper;
import uz.nt.firstspring.utils.DateUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public static HashMap<Long, UserInfoDto> users = new HashMap<>();

    private final RedisRepository redisRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findFirstByUsername(username);

        Optional<UserInfoDto> userInfoDto = user.map(userMapper::toDto);

        userInfoDto.ifPresent(infoDto -> Locale.setDefault(new Locale(infoDto.getLocale())));

        return userInfoDto.orElse(null);
    }

    public ResponseDto<String> addUser(UserInfoDto userInfoDto){
        User user = userMapper.toEntity(userInfoDto);

        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);

        return ResponseDto.<String>builder()
                .success(true)
                .code(0)
                .message("OK")
                .data("Successfully saved")
                .build();
    }

    public ResponseDto<JWTResponseDTO> login(LoginDTO loginDTO) {

        User user = userRepository.findFirstByUsername(loginDTO.getUsername())
                .orElseThrow(() ->
                    new UsernameNotFoundException(String.format("User with username %s not found", loginDTO.getUsername()))
                );

        if (!encoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Password is incorrect");
        }

        try {
//            users.put(user.getId(), userMapper.toDto(user));
            redisRepository.save(new UserSession(user.getId(), userMapper.toDto(user)));

            String token = jwtUtil.generateToken(String.valueOf(user.getId()));

            return ResponseDto.<JWTResponseDTO>builder()
                    .data(new JWTResponseDTO(token, DateUtil.oneDay(), null))
                    .message("OK")
                    .success(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.<JWTResponseDTO>builder()
                    .message("Error in while generating token: " + e.getMessage())
                    .code(-2)
                    .build();
        }
    }

    public ResponseDto<UserInfoDto> checkToken(String token) {
        Long subject = Long.valueOf(String.valueOf(jwtUtil.getClaim(token, "sub")));

        return ResponseDto.<UserInfoDto>builder().
                data(users.get(subject))
                .success(true)
                .build();
    }
}
