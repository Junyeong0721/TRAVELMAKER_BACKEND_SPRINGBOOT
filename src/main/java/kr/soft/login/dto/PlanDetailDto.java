package kr.soft.login.dto;

import lombok.Data;

@Data
public class PlanDetailDto {
    private int day;
    private String time;
    private String category;
    private String title;
    private String address;
}