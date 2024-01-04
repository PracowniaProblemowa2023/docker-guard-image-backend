package pl.dockerguardimage.api.functionality.user.specification;

import com.google.common.base.Strings;
import org.springframework.data.jpa.domain.Specification;
import pl.dockerguardimage.api.functionality.common.specification.BaseSpecification;
import pl.dockerguardimage.data.functionality.user.domain.User;
import pl.dockerguardimage.security.functionality.user.context.UserContextHolder;

import static org.springframework.data.jpa.domain.Specification.where;

public class UserSpecification extends BaseSpecification<User, UserSearchCriteria> {
    @Override
    public Specification<User> getFilter(UserSearchCriteria request) {
        return where(email(request.payload())
                .or(username(request.payload()))
                .or(fullName(request.payload())))
                .and(notCurrentUser());
    }

    private Specification<User> notCurrentUser() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.not(root.get("id").in(UserContextHolder.getAuthenticatedUser().id()));
    }

    private Specification<User> email(String payload) {
        return (root, query, criteriaBuilder) -> {
            if (Strings.isNullOrEmpty(payload)) {
                return null;
            }
            return criteriaBuilder.like(root.get("email"), containsLowerCase(payload));
        };
    }

    private Specification<User> username(String payload) {
        return (root, query, criteriaBuilder) -> {
            if (Strings.isNullOrEmpty(payload)) {
                return null;
            }
            return criteriaBuilder.like(root.get("username"), containsLowerCase(payload));
        };
    }

    private Specification<User> fullName(String payload) {
        return (root, query, cb) -> {
            if (Strings.isNullOrEmpty(payload)) {
                return null;
            }
            return cb.like(cb.lower(cb.concat(root.get("firstname"), cb.concat(" ", root.get("lastname")))), containsLowerCase(payload));
        };
    }


}
