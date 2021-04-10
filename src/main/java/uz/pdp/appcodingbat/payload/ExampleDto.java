package uz.pdp.appcodingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ExampleDto {
    @NotNull(message = "text should not be null")
    private String text;

    @NotNull(message = "task id should not be null")
    private Integer taskId;
}
