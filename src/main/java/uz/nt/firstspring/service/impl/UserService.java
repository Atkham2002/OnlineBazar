package uz.nt.firstspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import uz.nt.firstspring.repository.UserRepository;
import uz.nt.firstspring.repository.UserSessionRepository;
import uz.nt.firstspring.security.JwtUtil;
import uz.nt.firstspring.service.mapper.UserMapper;
import uz.nt.firstspring.dto.JWTResponseDTO;
import uz.nt.firstspring.dto.LoginDTO;
import uz.nt.firstspring.dto.ResponseDto;
import uz.nt.firstspring.dto.UserInfoDto;
import uz.nt.firstspring.entity.User;
import uz.nt.firstspring.entity.UserSession;
import uz.nt.firstspring.utils.DateUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final UserSessionRepository userSessionRepository;
    private final ResourceBundle bundle;

    public static HashMap<Long, UserInfoDto> users = new HashMap<>();
    @Override
    @Transactional
    public UserInfoDto loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findFirstByUsername(username);

        Optional<UserInfoDto> userInfoDto = user.map(userMapper::toDto);

        if (!userInfoDto.isPresent()){
            throw new UsernameNotFoundException("User with username is not found");
        }

        if (userInfoDto.get().getLocale() == null) throw new NotFoundException("Locale for user is not found");

        Locale.setDefault(new Locale(userInfoDto.get().getLocale()));

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
                .data(bundle.getString("response.success"))
                .build();
    }

    public ResponseDto<JWTResponseDTO> login(LoginDTO loginDTO) {

        User user = userRepository.findFirstByUsername(loginDTO.getUsername())
                .orElseThrow(() ->
                    new UsernameNotFoundException(String.format(bundle.getString("response.login_not_found"), loginDTO.getUsername()))
                );

        if (!encoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new BadCredentialsException(bundle.getString("response.pass_incorrect"));
        }

        try {
//            users.put(user.getId(), userMapper.toDto(user));
            userSessionRepository.save(new UserSession(user.getId(), userMapper.toDto(user)));

            String token = jwtUtil.generateToken(String.valueOf(user.getId()));

            return ResponseDto.<JWTResponseDTO>builder()
                    .data(new JWTResponseDTO(token, DateUtil.oneDay(), null))
                    .message(bundle.getString("response.success"))
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
                data(userSessionRepository.findById(subject).get().getUserInfo())
                .success(true)
                .build();
    }
}
