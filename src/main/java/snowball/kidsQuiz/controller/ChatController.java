package snowball.kidsQuiz.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import snowball.kidsQuiz.domain.ChatDTO;
import snowball.kidsQuiz.domain.ChatRoomMap;
import snowball.kidsQuiz.service.MsgChatService;

import java.util.ArrayList;
@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations template;

    private final MsgChatService msgChatService;


    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDTO chat, SimpMessageHeaderAccessor headerAccessor) {


        String userUUID = msgChatService.addUser(ChatRoomMap.getInstance().getCharRooms(), chat.getRoomId(), chat.getSender());


        headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        chat.setMessage(chat.getSender() + " 님 Math Lab Tester 로 입장!!!");
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(),chat);
    }

    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDTO chat){
        log.info("CHAT {}",chat);
        chat.setMessage(chat.getMessage());
        template.convertAndSend("/sub/chat/room/"+chat.getRoomId(),chat);
    }

    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event){
        log.info("DisConnEvent {}",event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        log.info("headerAccessor {}",headerAccessor);

        String username = msgChatService.findUserNameByRoomIdAndUserUUID(ChatRoomMap.getInstance().getCharRooms(), roomId, userUUID);
        msgChatService.delUser(ChatRoomMap.getInstance().getCharRooms(), roomId,userUUID);

        if(username != null){
            log.info("USER Disconnected : " + username);

            ChatDTO chat = ChatDTO.builder()
                    .type(ChatDTO.MessageType.LEAVE)
                    .sender(username)
                    .message(username + "님이 나가셨습니다 :)")
                    .build();

            template.convertAndSend("/sub/chat/room/" + roomId,chat);
        }
    }

    @GetMapping("/chat/userlist")
    @ResponseBody
    public ArrayList<String> userList(String roomId) {
        return msgChatService.getUserList(ChatRoomMap.getInstance().getCharRooms(), roomId);
    }

    @GetMapping("/chat/duplicateNmae")
    @ResponseBody
    public String isDuplicateName(@RequestParam("roomId")String roomId,@RequestParam("username") String username){
        String userName = msgChatService.isDuplicateName(ChatRoomMap.getInstance().getCharRooms(),roomId,username);
        log.info("동작확인 {}", userName);
        return userName;
    }

}