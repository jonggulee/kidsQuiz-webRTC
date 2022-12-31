package snowball.kidsQuiz.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import snowball.kidsQuiz.domain.ChatRoomDTO;
import snowball.kidsQuiz.service.ChatService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final ChatService chatService;

    @RequestMapping("/")
    public String home(){
        log.info("home controller");

        return "home";
    }

    @GetMapping("/chat")
    public String goChatRoom(Model model) {
        model.addAttribute("list", chatService.findAllRoom());

        List<ChatRoomDTO> chatRoomDTOS =  chatService.findAllRoom();

        for (ChatRoomDTO chatRoomDTO : chatRoomDTOS) {
            System.out.println("chatRoomDTO.getRoomId() = " + chatRoomDTO.getRoomId());
            System.out.println("chatRoomDTO.getChatType() = " + chatRoomDTO.getChatType());
        }

        log.info("SHOW ALL ChatList {}",chatService.findAllRoom());
        return "chat/roomList";
    }
}