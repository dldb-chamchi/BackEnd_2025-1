package com.example.bcsd.requestDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberRequestDto {
    private String name;
    private String email;
    private String password;

    public MemberRequestDto() {}

}
