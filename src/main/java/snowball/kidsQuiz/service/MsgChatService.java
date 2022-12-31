package snowball.kidsQuiz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.standard.expression.GreaterOrEqualToExpression;
import snowball.kidsQuiz.domain.ChatRoomDTO;
import snowball.kidsQuiz.domain.ChatRoomMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



@Slf4j
@RequiredArgsConstructor
@Service
public class MsgChatService {
    public ChatRoomDTO createChatRoom(String roomName){
        ChatRoomDTO room = ChatRoomDTO.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .build();

        room.setUserList(new HashMap<String, String>());

        room.setChatType(ChatRoomDTO.ChatType.MSG);
        ChatRoomMap.getInstance().getCharRooms().put(room.getRoomId(), room);
        return room;
    }

    public String addUser(Map<String, ChatRoomDTO> chatRoomMap, String roomId, String userName){
        ChatRoomDTO room = chatRoomMap.get(roomId);
        String userUUID = UUID.randomUUID().toString();

        HashMap<String, String> userList = (HashMap<String, String>)room.getUserList();
        userList.put(userUUID, userName);
        return userUUID;
    }

    public String isDuplicateName(Map<String, ChatRoomDTO> chatRoomMap, String roomId, String username){
        ChatRoomDTO room = chatRoomMap.get(roomId);
        String tmp = username;

        while(room.getUserList().containsValue(tmp)){
            int ranNum = (int)(Math.random()*100) + 1;
            tmp = username + ranNum;
        }
        return tmp;
    }

    public String findUserNameByRoomIdAndUserUUID(Map<String, ChatRoomDTO> chatRoomMap, String roomId, String userUUID){
        ChatRoomDTO room = chatRoomMap.get(roomId);
        return (String)room.getUserList().get(userUUID);
    }

    public ArrayList<String> getUserList(Map<String, ChatRoomDTO> chatRoomMap, String roomId){
        ArrayList<String> list = new ArrayList<>();

        ChatRoomDTO room = chatRoomMap.get(roomId);
        room.getUserList().forEach((key, value) -> list.add((String)value));
        return list;
    }

    public void delUser(Map<String, ChatRoomDTO> chatRoomMap, String roomId, String userUUID){
        ChatRoomDTO room = chatRoomMap.get(roomId);
        room.getUserList().remove(userUUID);
    }
}
