package uz.pdp.appcodingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TaskDto {

    @NotNull
    private String name;

    @NotNull
    private String text;

    private String solution;

    @NotNull
    private String method;

    private String hint;

    private boolean hasStar=false;

    @NotNull
    private Integer categoryId;
}
