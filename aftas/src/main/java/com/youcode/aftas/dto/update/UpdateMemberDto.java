package com.youcode.aftas.dto.update;

import com.youcode.aftas.domain.enums.IdentityDocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateMemberDto implements Serializable {
    @NotNull(message = "num cannot be null.")
    @Positive(message = "num cannot be negative.")
    private Integer num;

    @NotNull(message = "name cannot be null.")
    @NotBlank(message = "name cannot be blank.")
    private String name;

    @NotNull(message = "family name cannot be null.")
    @NotBlank(message = "family name cannot be blank.")
    private String familyName;

    private LocalDate accessionDate;

    @NotNull(message = "nationality cannot be null.")
    @NotBlank(message = "nationality cannot be blank.")
    private String nationality;

    @NotBlank(message = "identity number cannot be blank.")
    private String identityNumber;

    private IdentityDocumentType identityDocument;
}