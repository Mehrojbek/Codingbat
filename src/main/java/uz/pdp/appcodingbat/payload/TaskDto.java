package uz.pdp.appcodingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TaskDto {

    @NotNull(message = "name should not be null")
    private String name;

    @NotNull(message = "text should not be null")
    private String text;

    private String solution;

    @NotNull(message = "method should not be null")
    private String method;

    private String hint;

    private boolean hasStar=false;

    @NotNull(message = "category id should not be null")
    private Integer categoryId;
}
