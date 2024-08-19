package com.dev.shopdienthoai.demo.domain.dto;

import com.dev.shopdienthoai.demo.until.constant.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
public class UserDTOCreate {
    private long id;
    private String name;

    private String email;
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant createdAt;
}
