package com.isaachahn.billedrelay.rest;

import com.isaachahn.billedrelay.models.entity.Router;
import com.isaachahn.billedrelay.payload.request.RouterPayload;
import com.isaachahn.billedrelay.service.RouterService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/router")
@RequiredArgsConstructor
public class RouterController {
    private final RouterService routerService;

    @PostMapping
    public ResponseEntity<Router> registerRouter(@RequestBody RouterPayload payload) throws BadRequestException {
        return ResponseEntity.ok(routerService.createRouter(payload));
    }


//    public void claimRouter() {
//
//    }
//
//    public ResponseEntity<Router> getRouter() {
//
//        //TODO
//        return null;
//    }
//
}
