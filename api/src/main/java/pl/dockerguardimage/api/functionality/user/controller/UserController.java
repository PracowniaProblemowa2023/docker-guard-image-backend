package pl.dockerguardimage.api.functionality.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dockerguardimage.api.functionality.user.mapper.ProfileMapper;
import pl.dockerguardimage.api.functionality.user.model.UserResponse;
import pl.dockerguardimage.api.functionality.user.specification.UserSearchCriteria;
import pl.dockerguardimage.api.functionality.user.specification.UserSpecification;
import pl.dockerguardimage.data.functionality.user.service.UserQueryService;

import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserQueryService userQueryService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll(@PageableDefault(size = 10, page = 0, sort = "firstname", direction = Sort.Direction.ASC) Pageable pageable, UserSearchCriteria criteria) {
        var filter = new UserSpecification().getFilter(criteria);
        return ResponseEntity.ok(ProfileMapper.map(userQueryService.getAll(filter, pageable)));
    }

}
