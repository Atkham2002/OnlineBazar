package uz.nt.firstspring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import uz.nt.firstspring.dto.UserInfoDto;

@RedisHash(value = "userSession", timeToLive = 60 * 60 * 24 * 1000)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSession {
    private Long id;
    private UserInfoDto userInfo;
}
