public enum ReservationStatus 
{
    CONFIRMED("Confirmed"),
    CHECKED_IN("Checked In"),
    CHECKED_OUT("Checked Out"),
    CANCELLED("Cancelled"),
    NO_SHOW("No Show");
    private final String displayName;
    ReservationStatus(String displayName) 
    {
        this.displayName = displayName;
    }
    @Override
    public String toString() 
    {
        return displayName;
    }
}