package de.mobilecompass.anappoficeandfire.modules.houses.network.models

import java.io.IOException

class HousesRemoteException(message: String?): IOException(message ?: "Unknown error") {
    companion object {
        val LOG_TAG: String = HousesRemoteException::class.java.simpleName
    }
}