public class SeasonalPricing implements PricingStrategy
{
  @Override
  public double calculatePrice(HotelRoom room, long days) 
  {
    double total = room.price * days;
    return total + (total * 0.20);
  }
  
}
