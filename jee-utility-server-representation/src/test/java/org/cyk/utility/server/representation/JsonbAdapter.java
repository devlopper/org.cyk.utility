package org.cyk.utility.server.representation;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.JsonbBuilder;

import org.cyk.utility.server.representation.entities.MyEntityDto;

public class JsonbAdapter implements javax.json.bind.adapter.JsonbAdapter<ArrayList<MyEntityDto>, JsonObject> {
    @Override
    public JsonObject adaptToJson(ArrayList<MyEntityDto> object) throws Exception {
    	System.out.println("AbstractEntityCollection.JsonbAdapter.adaptToJson() : "+object+" : "+object.getClass());
    	JsonObject jsonObject = Json.createObjectBuilder()
            .add("collection", JsonbBuilder.create().toJson(object))
        	//.add("id", c.getId())
            //.add("name", c.getName())
            .build();
    	System.out.println("AbstractEntityCollection.JsonbAdapter.adaptToJson() RESULT : "+jsonObject);
    	return jsonObject;
    }

    @Override
    public ArrayList<MyEntityDto> adaptFromJson(JsonObject jsonObject) throws Exception {
    	System.out.println("AbstractEntityCollection.JsonbAdapter.adaptFromJson() : "+jsonObject);
    	ArrayList<MyEntityDto> c = new ArrayList<>();
        //c.setId(adapted.getInt("id"));
        //c.setName(adapted.getString("name"));
        return c;
    }
}