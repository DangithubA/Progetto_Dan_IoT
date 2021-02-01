package com.station.cooking.cheese.message;

import java.util.Map;

/**
 * @author: Daniele Barbieri Powered by Marco Picone
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */

public class EventMessage extends GenericMessage {

    public EventMessage() {
    }

    public EventMessage(String type, Map<String, Object> metadata) {
        super(type, metadata);
    }

    public EventMessage(String type, long timestamp, Map<String, Object> metadata) {
        super(type, timestamp, metadata);
    }
}
