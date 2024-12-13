package com.dineth.debateTracker.dtos.xmlparsing;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SpeechDTO {
    private String speakerId;
    private boolean reply;
    private List<IndividualSpeechBallotDTO> individualSpeechBallots;
    private Long dbId;
    private Integer speakerPosition;

    public SpeechDTO(String speakerId, boolean reply, List<IndividualSpeechBallotDTO> individualSpeechBallots, Integer speakerPosition) {
        this.speakerId = speakerId;
        this.reply = reply;
        this.individualSpeechBallots = individualSpeechBallots;
        this.speakerPosition = speakerPosition;
    }

    @Override
    public String toString() {
        return "SpeechDTO{" +
                "speakerId='" + speakerId + '\'' +
                ", reply=" + reply +
                ", ballots=" + individualSpeechBallots +
                ", speakerPosition=" + speakerPosition +
                '}';
    }

}
