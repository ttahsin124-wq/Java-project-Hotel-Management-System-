public enum RoomCategory {
    SINGLE("Single Room"),
    DOUBLE("Double Room"),
    DELUXE("Deluxe Room"),
    SUITE("Suite");
    private final String displayName;
    RoomCategory(String displayName) 
    {
        this.displayName = displayName;
    }
    @Override
    public String toString() 
    {
        return displayName;
    }
}