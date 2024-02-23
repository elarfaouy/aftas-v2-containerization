package com.youcode.aftas.dto.payload;

import com.youcode.aftas.domain.enums.IdentityDocumentType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class MemberDto implements Serializable {
    private Integer num;
    private String name;
    private String familyName;
    private LocalDate accessionDate;
    private String nationality;
    private String identityNumber;
    private IdentityDocumentType identityDocument;
}