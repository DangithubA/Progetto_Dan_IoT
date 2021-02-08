package com.station.cooking.cheese.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SenMLPack extends ArrayList<SenMLRecord> {
}