package snowball.kidsQuiz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMessage {
    private String from;
    private String type;
    private String data;
    private Object candidate;
    private Object sdp;
}
