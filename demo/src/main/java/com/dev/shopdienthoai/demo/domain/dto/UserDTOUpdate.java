package com.dev.shopdienthoai.demo.domain.dto;

import com.dev.shopdienthoai.demo.until.constant.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
public class UserDTOUpdate {
    @NotBlank(message = "không được để trống id")
    private long id;
    private String name;
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant updatedAt;
}

