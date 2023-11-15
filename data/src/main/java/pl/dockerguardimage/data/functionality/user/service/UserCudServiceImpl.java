package pl.dockerguardimage.data.functionality.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.dockerguardimage.data.functionality.user.common.service.CudServiceImpl;
import pl.dockerguardimage.data.functionality.user.domain.User;

@Slf4j
@Service
public class UserCudServiceImpl extends CudServiceImpl<UserRepository, User, Long> implements UserCudService {
    public UserCudServiceImpl(UserRepository repository) {
        super(repository);
    }

}
