package com.example.backendpensionat.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;

public class BaseController  {
    protected static String VersionNumber = null;

    @Autowired
    private Environment env;

    public BaseController(){
    }

    protected void setupVersion(Model model) {
        if(VersionNumber == null){
            String buildnumber = env.getProperty("schedule-app.buildnumber");
            String sourceversion = env.getProperty("schedule-app.sourceversion");

            VersionNumber = buildnumber + "-" + sourceversion.substring(0,7);
        }

        model.addAttribute("VersionNumber", VersionNumber);
    }
}
