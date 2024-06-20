package com.lypanha.mybookstore.model;

public class Item {
    public static final int BOOK = 0;
    public static final int BOOK_SERIES = 1;
    private final int itemTypeId;
    private final Object object;

    public Item(int itemTypeId, Object object) {
        this.itemTypeId = itemTypeId;
        this.object = object;
    }

    public int getItemTypeId() {
        return itemTypeId;
    }

    public Object getObject() {
        return object;
    }
}
