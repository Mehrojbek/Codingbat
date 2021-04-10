package uz.pdp.appcodingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LanguageDto {
    @NotNull(message = "name should not be null")
    private String name;
}
