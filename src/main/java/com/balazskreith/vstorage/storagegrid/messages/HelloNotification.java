package com.balazskreith.vstorage.storagegrid.messages;

import java.util.UUID;

/**
 * All individual endpoint creates and regularly send id
 */
public record HelloNotification(UUID sourceEndpointId) {

}
