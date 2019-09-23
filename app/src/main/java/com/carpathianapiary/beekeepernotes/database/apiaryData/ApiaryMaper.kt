package com.carpathianapiary.beekeepernotes.database.apiaryData

fun ApiaryData.mapToApiary(ownerId: String): Apiary {
    return Apiary(
            this.name,
            ownerId,
            this.locationId,
            this.info,
            this.note
    )
}