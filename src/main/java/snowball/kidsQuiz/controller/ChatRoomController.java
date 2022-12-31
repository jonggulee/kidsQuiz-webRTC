package snowball.kidsQuiz.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import snowball.kidsQuiz.domain.ChatRoomDTO;
import snowball.kidsQuiz.domain.ChatRoomMap;
import snowball.kidsQuiz.service.ChatService;

import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    @PostMapping("/chat/createroom")
    public String createRoom(@RequestParam("roomName") String roomName,
                             @RequestParam("chatType") String chatType,
                             RedirectAttributes rttr) {


        ChatRoomDTO room;
        room = chatService.createChatRoom(roomName, chatType);
        log.info("CREATE Chat Room{}",room);


        rttr.addFlashAttribute("roomName", room);
        return "redirect:/chat";
    }

    @GetMapping("/chat/room")
    public String roomDetail(Model model, String roomId) {
        log.info("roomId {}", roomId);
        ChatRoomDTO room = ChatRoomMap.getInstance().getCharRooms().get(roomId);
        model.addAttribute("room", room);
        log.info("room {}",room);
//        System.out.println("room.getRoomId() = " + room.getRoomId());
//        System.out.println("room.getChatType() = " + room.getChatType());

        if(ChatRoomDTO.ChatType.MSG.equals(room.getChatType())){
            return "chat/chatroom";
        }else{
            model.addAttribute("uuid", UUID.randomUUID().toString());
            return "chat/rtcroom";
        }
//        return "redirect:/chat";
    }

}