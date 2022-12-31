package snowball.kidsQuiz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import snowball.kidsQuiz.domain.ChatRoomDTO;
import snowball.kidsQuiz.domain.ChatRoomMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class RtcChatService {

    public ChatRoomDTO createChatRoom(String roomName){

        ChatRoomDTO room = ChatRoomDTO.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .build();

        room.setUserList(new HashMap<String, WebSocketSession>());
        room.setChatType(ChatRoomDTO.ChatType.RTC);
        ChatRoomMap.getInstance().getCharRooms().put(room.getRoomId(), room);

        return room;
    }

    public Map<String ,WebSocketSession> getClients(ChatRoomDTO room){
        Optional<ChatRoomDTO> roomDto = Optional.ofNullable(room);

        return (Map<String, WebSocketSession>) roomDto.get().getUserList();
    }

    public Map<String, WebSocketSession> addClient(ChatRoomDTO room, String name, WebSocketSession session) {
        Map<String, WebSocketSession> userList = (Map<String, WebSocketSession>) room.getUserList();
        userList.put(name, session);
        return userList;
    }

    public void removeClientByName(ChatRoomDTO room, String userUUID) {
        room.getUserList().remove(userUUID);
    }

}