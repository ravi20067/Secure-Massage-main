package com.Security.Secure.Massage.Controller;

import com.Security.Secure.Massage.Entity.GroupMessage;
import com.Security.Secure.Massage.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupChatController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/{groupId}/messages")
    public List<GroupMessage> getGroupMessages(
            @PathVariable String groupId
    ) {
        return groupService.getGroupMessages(groupId);
    }
}

