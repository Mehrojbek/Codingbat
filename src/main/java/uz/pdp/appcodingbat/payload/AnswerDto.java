package uz.pdp.appcodingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnswerDto {
    @NotNull
    private String text;

    private Integer userId;

    @NotNull
    private Integer taskId;

}
