package sn.youdev.controller;

import org.springframework.http.ResponseEntity;

import static sn.youdev.config.Constante.jsonResponse;

public class BaseController {
    public ResponseEntity<Object>controllerResponse(Object obj, String message){
        return jsonResponse(true,obj,200,message);
    }
    public ResponseEntity<Object> controllerResponse(Object obj){
        return jsonResponse(true,obj,200,null);
    }
}
