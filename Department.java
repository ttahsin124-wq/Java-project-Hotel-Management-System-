public enum Department 
{
    RECEPTION("Reception"),
    HOUSEKEEPING("Housekeeping"),
    KITCHEN("Kitchen"),
    MAINTENANCE("Maintenance"),
    SECURITY("Security"),
    MANAGEMENT("Management"),
    FRONT_DESK("Front Desk");
    private final String displayName;
    Department(String displayName)
    {
        this.displayName = displayName;
    }
    @Override
    public String toString() 
    {
        return displayName;
    }
}