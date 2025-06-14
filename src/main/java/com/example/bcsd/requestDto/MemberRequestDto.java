package com.example.bcsd.requestDto;

public class MemberRequestDto {
    private String name;
    private String email;
    private String password;

    public MemberRequestDto() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
