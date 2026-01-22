package com.Security.Secure.Massage.Controller;

import com.Security.Secure.Massage.Entity.Group;
import com.Security.Secure.Massage.Repository.GroupMemberRepository;
import com.Security.Secure.Massage.Repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping
    public List<Group> getMyGroups(Authentication authentication) {

        String email = authentication.getName();

        return groupMemberRepository.findByUserEmail(email)
                .stream()
                .map(m -> groupRepository.findById(m.getGroupId()).orElse(null))
                .filter(g -> g != null)
                .toList();
    }
}

