package com.example.bcsd.Model;

public class Member {
    private int memberId;
    private String name;
    private String email;
    private String password;

    public Member(int id, String name){
        if(name == null){ throw new RuntimeException();}
        this.memberId = id;
        this.name = name;
    }
    public int getMemberId() {return memberId;}
    public String getName() {return name;}

}
