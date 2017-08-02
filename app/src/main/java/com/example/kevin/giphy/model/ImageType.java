package com.example.kevin.giphy.model;

public enum ImageType {


    FIXED_HEIGHT("fixed_height"),
    FIXED_HEIGHT_STILL("fixed_height_still"),
    FIXED_HEIGHT_DOWNSAMPLED("fixed_height_downsampled"),
    FIXED_WIDTH("fixed_width"),
    FIXED_WIDTH_STILL("fixed_width_still"),
    FIXED_WIDTH_DOWNSAMPLED("fixed_width_downsampled"),
    FIXED_HEIGHT_SMALL("fixed_height_small"),
    FIXED_HEIGHT_SMALL_STILL("fixed_height_small_still"),
    FIXED_WIDTH_SMALL("fixed_width_small"),
    FIXED_WIDTH_SMALL_STILL("fixed_width_small_still"),
    PREVIEW("preview"),
    DOWNSIZED_SMALL("downsized_small"),
    DOWNSIZED("downsized"),
    DOWNSIZED_MEDIUM("downsized_medium"),
    DOWNSIZED_LARGE("downsized_large"),
    DOWNSIZED_STILL("downsized_still"),
    ORIGINAL("original"),
    ORIGINAL_STILL("original_still"),
    LOOPING("looping");

    private final String value;

    ImageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
