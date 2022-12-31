package snowball.kidsQuiz.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class ChatRoomDTO {
    @NotNull
    private String roomId;
    private String roomName;
    private long userCount;

    public enum ChatType{
        MSG, RTC
    }
    private ChatType chatType;
    private HashMap<String, ?> userList;
}
