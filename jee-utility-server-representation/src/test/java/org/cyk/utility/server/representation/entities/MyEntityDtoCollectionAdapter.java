package org.cyk.utility.server.representation.entities;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class MyEntityDtoCollectionAdapter implements JsonbAdapter<Object, JsonObject> {
    @Override
    public JsonObject adaptToJson(Object c) throws Exception {
        return Json.createObjectBuilder()
            //.add("id", c.getId())
            //.add("name", c.getName())
            .build();
    }

    @Override
    public Object adaptFromJson(JsonObject adapted) throws Exception {
    	Object c = new Object();
        //c.setId(adapted.getInt("id"));
        //c.setName(adapted.getString("name"));
        return c;
    }
}