package uz.nt.firstspring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import uz.nt.firstspring.dto.UserInfoDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "userSession",timeToLive = 4 * 60)
public class UserSession {

    private Long id;
    private UserInfoDto userInfoDto;

}
