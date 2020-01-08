package com.practice.machineservice.utils;

import com.practice.machineservice.model.Machine;

public class TestUtils {
    public static final Long ORIGINAL_ID = 1L;
    public static final Long UPDATE_ID = 2L;
    public static final Machine UNSAVED_MACHINE = new Machine("plating1", "Gold plating machine #1", 10);
    public static final Machine UNSAVED_MACHINE_2 = new Machine("plating2", "Gold plating machine #2", 20);
    public static final Machine UNSAVED_MACHINE_3 = new Machine("plating3", "Silver plating machine #1", 10);
    public static final Machine MACHINE = new Machine(ORIGINAL_ID, "plating1", "Gold plating machine #1", 10);
    public static final Machine UPDATED_MACHINE = new Machine(ORIGINAL_ID, "plating1new", "Gold plating machine #1.1", 11);
    public static final Machine UPDATE_DESC_MACHINE = new Machine(UPDATE_ID, "plating1", "Version 2 plating machine #2", 10);
    public static final Machine UPDATE_NAME_MACHINE = new Machine(UPDATE_ID, "plating2", "Gold plating machine #1", 10);
    public static final Machine UPDATE_THROUGHPUT_MACHINE = new Machine(UPDATE_ID, "plating1", "Gold plating machine #1", 20);

    public static final String UPDATE_MACHINE_DESC_JSON = "{\"description\": \"Version 2 plating machine #2\"}";
    public static final String UPDATE_MACHINE_NAME_JSON = "{\"name\": \"Gold plating machine #1\"}";
    public static final String UPDATE_MACHINE_THROUGHPUT_JSON = "{\"throughputMins\": \"20\"}";

    public static final String INVALID_NAME_MACHINE_JSON = "{\"name\": \"machine_having_more_than_40_characters_10\"," +
            "\"description\": \"some description\"," +
            "\"throughputMins\":\"100\"}";

    public static final String INVALID_THROUGHPUT_MACHINE_JSON = "{\"name\": \"plating10\"," +
            "\"description\": \"some description\"," +
            "\"throughputMins\":\"0\"}";

}
