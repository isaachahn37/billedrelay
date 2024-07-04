package com.isaachahn.billedrelay.service;

import com.isaachahn.billedrelay.repository.RelayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelayService {
    private final RelayRepository relayRepository;


}
