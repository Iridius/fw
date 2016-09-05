package model;

import java.util.HashMap;

public class UserFormColumn {
    private HashMap<String, String> _attributes;

    public UserFormColumn(String key){
        _attributes = new HashMap<>();
        _attributes.put("key", key.toLowerCase());
    }

    public String get(final String key){
        return _attributes.get(key.toLowerCase());
    }

    public void put(final String key, final String value){
        _attributes.put(key.toLowerCase(), value.toLowerCase());
    }

    @Override
    public String toString(){
        return _attributes.get("key");
    }

    public String getCaption(){
        return get("caption") == null ? "" : get("caption");
    }

    public Boolean getVisible() {
        return get("visible") == null ? false : Boolean.valueOf(get("visible"));
    }

    public String getTooltip() {
        return get("tooltip") == null ? getCaption() : get("tooltip");
    }
}
