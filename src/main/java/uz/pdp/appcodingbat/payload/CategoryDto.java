package uz.pdp.appcodingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryDto {
    @NotNull(message = "name should not be null")
    private String name;

    @NotNull(message = "description should not be null")
    private String dicription;

    @NotNull(message = "language id should not be null")
    private Integer languageId;
}
