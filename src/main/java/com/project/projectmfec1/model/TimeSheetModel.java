package com.project.projectmfec1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSheetModel {

    private Long id;
    private String date;
    private String startTime;
    private String endTime;
    private String activity;

}
