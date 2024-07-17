package com.isaachahn.billedrelay.util;

import com.isaachahn.billedrelay.models.entity.Relay;
import com.isaachahn.billedrelay.payload.response.RelayResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Util {
    public static RelayResponse mapToRelayResponse(Relay relay) {
        boolean onState = false;
        long currentTimeMillis = System.currentTimeMillis();
        long relayMilis = relay.getOnUntil() != null ? relay.getOnUntil() : 0L;
        if (relay.isForcedOn() || currentTimeMillis < relayMilis) {
            onState = true;
        }
        String dateString = convertMillisecondsToGMT7(relay.getOnUntil());
        return new RelayResponse().setRelayDescription(relay.getRelayDescription())
                .setId(relay.getId())
                .setRelayWhitelist(relay.getRelayWhitelist())
                .setRelayName(relay.getRelayName())
                .setRentalEntity(relay.getRentalEntity())
                .setPinNumber(relay.getPinNumber())
                .setRelayHardId(relay.getRelayHardId())
                .setOnState(onState)
                .setOnUntil(dateString)
                .setForcedOn(relay.isForcedOn());
    }

    public static String convertMillisecondsToGMT7(long milliseconds) {
        // Create a SimpleDateFormat instance with the desired format
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

        // Set the timezone to GMT+7
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));

        // Create a Date object from the milliseconds
        Date date = new Date(milliseconds);

        // Format the date
        return sdf.format(date);
    }
}
