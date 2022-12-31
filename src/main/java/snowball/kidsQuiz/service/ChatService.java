package snowball.kidsQuiz.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import snowball.kidsQuiz.domain.ChatRoomDTO;
import snowball.kidsQuiz.domain.ChatRoomMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final MsgChatService msgChatService;
    private final RtcChatService rtcChatService;

    public List<ChatRoomDTO> findAllRoom(){
        List<ChatRoomDTO> chatRooms = new ArrayList<>(ChatRoomMap.getInstance().getCharRooms().values());
        Collections.reverse(chatRooms);

        return chatRooms;
    }

    public ChatRoomDTO findRoomById(String roomId){
        return ChatRoomMap.getInstance().getCharRooms().get(roomId);
    }

    public ChatRoomDTO createChatRoom(String roomName, String chatType) {
        ChatRoomDTO room;

        if(chatType.equals("msgChat")){
            room = msgChatService.createChatRoom(roomName);
        }else{
            room = rtcChatService.createChatRoom(roomName);
        }
        return room;
    }
}
