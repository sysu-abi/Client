package com.sysu.ceres.model;

import java.util.ArrayList;
import java.util.List;

public class MessageList {
    private List<Message> messages = new ArrayList<>();

    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> value) { this.messages = value; }
}
