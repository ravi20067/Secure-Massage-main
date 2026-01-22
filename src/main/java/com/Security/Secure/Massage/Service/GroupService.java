package com.Security.Secure.Massage.Service;

import com.Security.Secure.Massage.Entity.Group;
import com.Security.Secure.Massage.Entity.GroupMember;
import com.Security.Secure.Massage.Entity.GroupMessage;
import com.Security.Secure.Massage.Repository.GroupMemberRepository;
import com.Security.Secure.Massage.Repository.GroupMessageRepository;
import com.Security.Secure.Massage.Repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private GroupMessageRepository groupMessageRepository;

    // create new group
    public Group createGroup(String name, String adminEmail) {

        Group group = new Group();
        group.setName(name);
        group.setAdminEmail(adminEmail);
        group.setCreatedAt(LocalDateTime.now());

        Group savedGroup = groupRepository.save(group);

        // add admin as member
        GroupMember member = new GroupMember();
        member.setGroupId(savedGroup.getId());
        member.setUserEmail(adminEmail);
        member.setRole("ADMIN");
        member.setJoinedAt(LocalDateTime.now());

        groupMemberRepository.save(member);

        return savedGroup;
    }

    // add user to group
    public void addMember(String groupId, String userEmail) {

        if (groupMemberRepository.existsByGroupIdAndUserEmail(groupId, userEmail)) {
            return;
        }

        GroupMember member = new GroupMember();
        member.setGroupId(groupId);
        member.setUserEmail(userEmail);
        member.setRole("MEMBER");
        member.setJoinedAt(LocalDateTime.now());

        groupMemberRepository.save(member);
    }

    // send group message
    public GroupMessage saveGroupMessage(
            String groupId,
            String senderEmail,
            String encryptedContent,
            String messageType
    ) {

        GroupMessage msg = new GroupMessage();
        msg.setGroupId(groupId);
        msg.setSenderEmail(senderEmail);
        msg.setEncryptedContent(encryptedContent);
        msg.setMessageType(messageType);
        msg.setTimestamp(LocalDateTime.now());

        return groupMessageRepository.save(msg);
    }

    // get group chat history
    public List<GroupMessage> getGroupMessages(String groupId) {
        return groupMessageRepository.findByGroupIdOrderByTimestampAsc(groupId);
    }
}

