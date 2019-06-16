package org.cyk.utility.__kernel__.maven.pom;

import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;

public class MapAdapter extends XmlAdapter<MapWrapper, Map<String, String>>{

    @Override

    public Map<String, String> unmarshal(MapWrapper v) throws Exception {

        Map<String, String> map = v.toMap();

        return map;

    }

    @Override

    public MapWrapper marshal(Map<String, String> m) throws Exception {

        MapWrapper wrapper = new MapWrapper();

        for(Map.Entry<String, String> entry : m.entrySet()){

             wrapper.addEntry(new JAXBElement<String>(new QName(entry.getKey()), String.class, entry.getValue()));

        }

        return wrapper;

    }

}