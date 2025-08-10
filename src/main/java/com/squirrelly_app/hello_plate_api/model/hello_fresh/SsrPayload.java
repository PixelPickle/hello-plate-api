package com.squirrelly_app.hello_plate_api.model.hello_fresh;

import lombok.Data;

import java.util.List;

@Data
public class SsrPayload {

    List<MenuCourse> courses;

}
