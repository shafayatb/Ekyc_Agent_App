package com.gigatech.ekyc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgentModel {

    @SerializedName("me")
    @Expose
    private Agent agent;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

}
