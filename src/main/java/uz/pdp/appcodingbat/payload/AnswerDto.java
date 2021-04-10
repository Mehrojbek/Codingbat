package uz.pdp.appcodingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnswerDto {
    @NotNull(message = "the text should not be empty")
    private String text;

    private Integer userId;

    @NotNull(message = "the task id should not be empty")
    private Integer taskId;

}
