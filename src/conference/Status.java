package conference;

/**
 * вместо да използвам
 * String status = "newStatus" / "processing" / etc.
 * използвам enum - Status status = Status.newStatus / Status.processing / etc.
 */
enum Status {
    newStatus, // нова (няма назначен рецензент)
    processing, // процес на рецензиране (има назначен рецензент, но още не е готова)
    readyAccepted,
    readyRejected,
}
