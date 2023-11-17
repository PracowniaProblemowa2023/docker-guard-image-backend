package pl.dockerguardimage.data.functionality.role.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.role.domain.Role;

import java.util.Collection;
import java.util.Set;


@Transactional(readOnly = true)
@AllArgsConstructor
@Service
class RoleQueryServiceImpl implements RoleQueryService {

    private final RoleRepository repository;

    @Override
    public Set<Role> getAllByNames(Collection<String> names) {
        return repository.findAllByNames(names);
    }


}
