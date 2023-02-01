package nextstep.waiting.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WaitingCounter {

    private Long waitingCount;
    private Long scheduleId;

    public void increase() {
        this.waitingCount++;
    }
}
