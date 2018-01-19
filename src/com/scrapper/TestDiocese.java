package com.scrapper;

import java.util.List;

/**
 * Created by joshuaschappel on 12/8/17.
 */
public class TestDiocese {
    private String state;
    private String dioceseName;

    protected TestDiocese (String state, String dioceseName){
        this.state = state;
        this.dioceseName = dioceseName;
    }

    protected String getState() {
        return state;
    }

    protected String getDioceseName() {
        return dioceseName;
    }

    public String toString(){
        return state + " : " + dioceseName;
    }


}
