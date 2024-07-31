package com.isaachahn.billedrelay.util;

import com.isaachahn.billedrelay.models.entity.PackageApplied;
import com.isaachahn.billedrelay.models.entity.Relay;
import com.isaachahn.billedrelay.models.entity.RentalPackage;
import com.isaachahn.billedrelay.payload.response.PackageAppliedReport;
import com.isaachahn.billedrelay.payload.response.RelayResponse;
import com.isaachahn.billedrelay.payload.response.RentalPackagePayload;
import org.apache.coyote.BadRequestException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.TimeZone;

public class Util {

    public static RentalPackage mapToRentalPackage(RentalPackagePayload rentalPackagePayload) {
        return new RentalPackage()
                .setId(rentalPackagePayload.getId())
                .setPackageName(rentalPackagePayload.getPackageName())
                .setMilisAddedOnTime(rentalPackagePayload.getAddedMinutes() * 60000)
                .setPrice(rentalPackagePayload.getPrice())
                .setRelayWhitelist(rentalPackagePayload.getRelayWhitelist());
    }

    public static RentalPackagePayload mapToRentalPackagePayload(RentalPackage rentalPackage) {
        return new RentalPackagePayload()
                .setId(rentalPackage.getId())
                .setPackageName(rentalPackage.getPackageName())
                .setAddedMinutes(rentalPackage.getMilisAddedOnTime() / 60000)
                .setPrice(rentalPackage.getPrice())
                .setRelayWhitelist(rentalPackage.getRelayWhitelist())
                .setRentalEntity(rentalPackage.getRentalEntity())
                .setLastModifiedDate(convertMillisecondsToGMT7(rentalPackage.getLastModified()));
    }

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

    public static PackageAppliedReport mapToPackageAppliedResponse(PackageApplied packageApplied) {
        return new PackageAppliedReport()
                .setDateTimeApplied(convertMillisecondsToGMT7(packageApplied.getAppliedTimeStamp()))
                .setPackageName(packageApplied.getRentalPackage().getPackageName())
                .setMinutesAdded(packageApplied.getRentalPackage().getMilisAddedOnTime() / 60000L)
                .setPackageAppliedAmount(packageApplied.getRentalPackage().getPrice())
                .setRelayName(packageApplied.getRelay().getRelayName())
                .setRelayHardId(packageApplied.getRelay().getRelayHardId())
                .setRelayWhitelist(packageApplied.getRelay().getRelayWhitelist())
                .setRelayDescription(packageApplied.getRelay().getRelayDescription());
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

    public static long getBeginningOfDayTimestamp(String dateStr) throws BadRequestException {
        return getTimestamp(dateStr, true) * 1000;
    }

    public static long getEndOfDayTimestamp(String dateStr) throws BadRequestException {
        return getTimestamp(dateStr, false) * 1000;
    }

    private static long getTimestamp(String dateStr, boolean isBeginningOfDay) throws BadRequestException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate localDate = LocalDate.parse(dateStr, formatter);
            ZoneId zoneId = ZoneId.of("GMT+7");
            ZonedDateTime zonedDateTime;

            if (isBeginningOfDay) {
                zonedDateTime = localDate.atStartOfDay(zoneId);
            } else {
                zonedDateTime = localDate.atTime(LocalTime.MAX).atZone(zoneId);
            }

            return zonedDateTime.toEpochSecond();
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid date format. Please use dd-MM-yyyy format.", e);
        }
    }
}
