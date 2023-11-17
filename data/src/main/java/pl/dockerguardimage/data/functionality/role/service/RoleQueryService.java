package pl.dockerguardimage.data.functionality.role.service;


import pl.dockerguardimage.data.functionality.role.domain.Role;

import java.util.Collection;
import java.util.Set;

public interface RoleQueryService {
    Set<Role> getAllByNames(Collection<String> names);
}
