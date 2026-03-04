public class DiscountPricing implements PricingStrategy
{
  @Override
  public double calculatePrice(HotelRoom room, long days) 
  {
    return room.price * days;
  }
}
