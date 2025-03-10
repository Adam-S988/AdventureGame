package com.keyin;

public class BookItem extends Item {
    private String content;

    public BookItem(String name, String description, String content) {
        super(name, description);
        this.content = content;
    }

    public void read() {
        System.out.println("Reading \u001B[33m" + getName() + "\u001B[0m:");
        System.out.println(content);
    }
}
