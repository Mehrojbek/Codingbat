package uz.pdp.appcodingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ExampleDto {
    @NotNull
    private String text;

    @NotNull
    private Integer taskId;
}
